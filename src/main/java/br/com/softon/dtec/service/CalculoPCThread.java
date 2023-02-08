package br.com.softon.dtec.service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.dao.ApontamentosDAO;
import br.com.softon.dtec.dao.RegrasDAO;
import br.com.softon.dtec.entity.Apontamento;
import br.com.softon.dtec.entity.Sublote;

public class CalculoPCThread implements Callable<Integer> {

	private final static Logger log = LoggerFactory
			.getLogger(CalculoPCThread.class);
	
	private Queue<Apontamento> apontamentosAtualizados;

	private Sublote sublote;

	private Integer pagePoll;

	private long qtdRowsPerPage;

	public CalculoPCThread(Queue<Apontamento> apontamentosAtualizados, Integer pagePoll, long qtdRowsPerPage, Sublote sublote) {
		this.apontamentosAtualizados = apontamentosAtualizados;
		this.sublote = sublote;
		this.pagePoll = pagePoll;
		this.qtdRowsPerPage = qtdRowsPerPage;
	}

	public Integer call() throws Exception {
		log.debug("Processando página " + pagePoll + " para esta Thread do cálculo do ponto de corte...");
		
		ApontamentosDAO apontamentosDao = new ApontamentosDAO();

        final Queue<Apontamento> apontamentos = apontamentosDao.buscarApontamentosLote(sublote, null, false, pagePoll, qtdRowsPerPage);
    	
    	// Para evitar NPE, validamos a busca da variável global do ponto de corte...
    	long qtdDiaCarencia = 
    			(Long) (RegrasDAO.carregarParametrosGlobaisValor() == null ? 0 : 
					RegrasDAO.carregarParametrosGlobaisValor().get("QT_DIA_CARENCIA") == null ? 0 :
						RegrasDAO.carregarParametrosGlobaisValor().get("QT_DIA_CARENCIA"));
    	
    	List<String> arrayEventosFimInvestiga = apontamentosDao.getEventosFimInvestiga();
    	
    	int count = apontamentos.size();
        
        for(Apontamento apontamento : apontamentos) {
        	// Setar flag de corte...  (Para evitar NPE, tratamos os valores para transformar em zero o que vier nulo)
        	boolean flagCorte = (apontamento.getValorApontamento() == null ? 0 : apontamento.getValorApontamento())
        						>= (apontamento.getValorPontoCorte() == null ? 0 : apontamento.getValorPontoCorte());
        	
        	// Verificar se existe regra gravíssima apontada...
        	boolean existeRegraGravissima = apontamentosDao.existeRegraGravissima(apontamento, null, false);
        	
        	// Verificar carência...
        	Boolean carencia = false;
        	
			// Só iremos verificar a carência se o cliente passar do ponto de corte e não tiver regra gravíssima...
    		if(flagCorte && !existeRegraGravissima) {
    			// Buscar regras por data de apontamento para o cliente a ser analisado a carência 
    			// levando em conta o parâmetro de tempo de carência e buscando apenas os clientes que não tiverem status de 
    			// movidos pelo sistema, a saber, status 1 e 30 (regra implementada na própria query de busca)...
    			Map<Timestamp, Queue<Integer>> mapaRegrasApontamentosAnteriores = 
    					apontamentosDao.buscarRegrasApontamentosAnteriores(qtdDiaCarencia, arrayEventosFimInvestiga,
    							apontamento.getCodTipoIdentificacaoCliente(), apontamento.getCodDocIdentificacaoCliente());
    			
    			// Buscar regras do apontamento corrente para ser analisado a carência...
    			Queue<Integer> regrasApontamentoAtual = 
    					apontamentosDao.buscarRegrasApontamentoCorrente(apontamento);    			
    			
    			for(Entry<Timestamp,Queue<Integer>> mapa : mapaRegrasApontamentosAnteriores.entrySet()) {
    				Queue<Integer> listaRegrasApontamentoAnterior = mapa.getValue();
    				Queue<Integer> copiaListaRegrasApontamentoAtual = new LinkedList<Integer>();
    				copiaListaRegrasApontamentoAtual.addAll(regrasApontamentoAtual);
    				for(Integer regra : listaRegrasApontamentoAnterior) {
    					if(copiaListaRegrasApontamentoAtual.contains(regra)) {
    						listaRegrasApontamentoAnterior.remove(regra);
    						copiaListaRegrasApontamentoAtual.remove(regra);
    					}
    				}
    				if(listaRegrasApontamentoAnterior.isEmpty() && copiaListaRegrasApontamentoAtual.isEmpty()) {
    					carencia = true;
    				}
    			}
    		}
    		
    		byte suspeitoLavagemDinheiro = (byte) (existeRegraGravissima ? 1 : 
    			(flagCorte ? (carencia ? 0 : 1) // Mesmo que o apontamento tenha passado o ponto de corte
					// será necessário verificar a carência, pois se houve apontamento anterior durante a carência este apontamento
					// terá o flag de suspeito de lavagem de dinheiro = falso
					: 0) );
    		
    		Apontamento apontamentoAtualizado = new Apontamento(
    			apontamento.getCodDocIdentificacaoCliente(), 
    			apontamento.getCodigoLote(), 
    			suspeitoLavagemDinheiro == (byte) 1 ? (byte) 0 : (byte) 1, 
    				// quando o valor do flagSuspeitoLD for 1 (true) o status será 0 - liberado pela analise para o analista humano
    				// quando o valor do flagSuspeitoLD for 0 (false) o status será 1 - Cliente não apontado
    			apontamento.getCodTipoIdentificacaoCliente(), 
    			apontamento.getDataApontamento(), // data do apontamento
    			apontamento.getDataAtualizacaoCalculo(), // data de atualização do cálculo
    			flagCorte ? 
    					(byte) (carencia ? 0 : 1) : // flagCarencia - Se for true a carência será 0 (false), indicando que existe apontamento 
    			// anterior idêntico ao apontamento atual dentro do período de carência, caso contrário a carência será 1 (true), indicando
    			// que o cliente será apontado pois não existem apontamentos iguais no passado dentro do período de carência.
        				null, // Se o apontamento não passou do ponto de corte, a carência será nula.
    			(byte) (flagCorte ? 1 : 0), // flagPontoCorte
    			suspeitoLavagemDinheiro, //flagSuspeitoLD
    			apontamento.getValorApontamento(), // valorApontamento
    			apontamento.getValorPontoCorte());
    		apontamentosAtualizados.add(apontamentoAtualizado);
        }
        
		log.info("Página " + pagePoll + " finalizada para esta Thread do cálculo do ponto de corte com " + count + " apontamentos calculados...");

        return 0;
	}

}
