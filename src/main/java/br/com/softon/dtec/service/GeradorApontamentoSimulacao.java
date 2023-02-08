package br.com.softon.dtec.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import br.com.softon.dtec.dao.ApontamentosDAO;
import br.com.softon.dtec.entity.Apontamento;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.DetalheApontamento;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.entity.Sublote;
import br.com.softon.dtec.entity.TransacaoAlertada;
import br.com.softon.dtec.entity.ObjetoAnalise;

public class GeradorApontamentoSimulacao implements GeradorAlerta {

    private final Queue<ObjetoAnalise> transacao = new ConcurrentLinkedQueue<ObjetoAnalise>();
    private final Queue<Apontamento> apontamentos = new ConcurrentLinkedQueue<Apontamento>();
    private final Map<String,Queue<TransacaoAlertada>> mapaClienteTransacoes = new HashMap<String, Queue<TransacaoAlertada>>();
    private final RegraHandle regras;
	private ControleSimulacao loteSimulacao;

    public GeradorApontamentoSimulacao(final RegraHandle regras, ControleSimulacao loteSimulacao) {
        this.regras = regras;
		this.loteSimulacao = loteSimulacao;
    }

    /**
     * @param regras
     * @throws Exception 
     */
    public void conclusaoAnalise(Sublote sublote) throws Exception {
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
        
    	//TODO! MUDAR CRIACAO DE REGISTROS DE APONTAMENTO E DETALHE
    	
        // Variável local que conterá todos os apontamentos para o sublote em processamento
        final Map<String,Apontamento> mapaApontamentos = apontamentosDao.buscarApontamentos(sublote, loteSimulacao, true);
                
        Queue<String> listaRegrasNaoAcumulativas = new LinkedList<String>();
        for(Entry<String,Apontamento> entry : mapaApontamentos.entrySet()) {
        	Apontamento apontamento = entry.getValue();
        	listaRegrasNaoAcumulativas.addAll(apontamentosDao.buscarListaRegrasNaoAcumulativas(apontamento, loteSimulacao, true));
        }
    	
    	// Para evitar NPE, validamos a busca da variável global do ponto de corte...
    	long valorPontoCorte = loteSimulacao.getValorPontoCorte();
    	
        while (transacao.size() > 0) {
            ObjetoAnalise tx = transacao.poll();
            TransacaoAlertada transacaoAlertada = new TransacaoAlertada(tx);
            
            // Será necessário agrupar as transações por cliente para gravar o apontamento...
            // Neste caso, usamos um mapa com a chave sendo uma composição do tipo de documento identificador do cliente e 
            // o número do documento identificador do cliente - o mesmo get usado no redis para trabalhar com o recuo relacionado.
            if(mapaClienteTransacoes.containsKey(tx.getCodigoIdentificadorCliente())) {
            	final Queue<TransacaoAlertada> transacoesAlertadas = mapaClienteTransacoes.get(tx.getCodigoIdentificadorCliente());
            	transacoesAlertadas.add(transacaoAlertada);
            	mapaClienteTransacoes.put(tx.getCodigoIdentificadorCliente(), transacoesAlertadas);
            } else {
                final Queue<TransacaoAlertada> transacoesAlertadas = new ConcurrentLinkedQueue<TransacaoAlertada>();
            	transacoesAlertadas.add(transacaoAlertada);
            	mapaClienteTransacoes.put(tx.getCodigoIdentificadorCliente(), transacoesAlertadas);
            }

            // Como as regras adicionam apenas o código, é necessário buscar o objeto Regra 
            // para pegar o valor de pontuação de cada regra e somar para comparar com o ponto de corte...
            for (Entry<Short, Boolean> entry : tx.getRegras().entrySet()) {
                final short coRegra = entry.getKey();
                Regra regra = regras.getRegra(coRegra);
                DetalheApontamento detalheApontamento = new DetalheApontamento(
                		regra.getCodigoClasseRegra(), 
                		tx.getCodDocIdentificacaoCliente(), 
                		sublote.getCoLote(), 
                		regra.getCodigoRegra(), 
                		tx.getCodTipoIdentificacaoCliente(), 
                		regra.getCodigoVersaoSistema(), 
                		tx.getInformacoesPorRegra(coRegra), // TODO - Implementar informações de Análise: informações no formato json com os parâmetros utilizados no apontamento da transação...
                		new Timestamp(new Date().getTime()),
                		tx.getDataApontamento(),
                		regra.getValorPontuacao() == 4,
                		(short) regra.getValorPontuacao()
                		);
                // Para tratar das regras acumulativas, usamos a lista de regras não acumulativas juntamente com o cliente para verificar se somaremos
                // a pontuação ou se apenas deixaremos o valor de pontuação original.  Maiores detalhes verificar o manual de especificação na
                // parte de regras acumulativas.
                if(regra.getFlagAcumulaRegra() == 1) {
                	transacaoAlertada.adicionarDetalheApontamento(detalheApontamento, (int) regra.getValorPontuacao());
                } else {
	                if(listaRegrasNaoAcumulativas.contains(tx.getCodTipoIdentificacaoCliente() + 
	                		tx.getCodDocIdentificacaoCliente() + 
	                		regra.getCodigoRegra())) {
	                	transacaoAlertada.adicionarDetalheApontamento(detalheApontamento, 0);
	                } else {
	                	transacaoAlertada.adicionarDetalheApontamento(detalheApontamento, (int) regra.getValorPontuacao());
	                	listaRegrasNaoAcumulativas.add(tx.getCodTipoIdentificacaoCliente() + 
		                		tx.getCodDocIdentificacaoCliente() + 
		                		regra.getCodigoRegra());
	                }
                }
            }
        }
        
        for(Entry<String,Queue<TransacaoAlertada>> entry : mapaClienteTransacoes.entrySet()) {
        	Apontamento apontamento = null;
        	Queue<TransacaoAlertada> valor = entry.getValue();
        	short pontuacaoCliente = 0;
        	byte codTipoIdentificacaoCliente = 0;
        	String codDocIdentificacaoCliente = null;
        	Timestamp dataApontamento = null;
        	for(TransacaoAlertada tx : valor) {
        		pontuacaoCliente += tx.getValorPontuacaoTotal();
        		codTipoIdentificacaoCliente = tx.getTransacao().getCodTipoIdentificacaoCliente();
        		codDocIdentificacaoCliente = tx.getTransacao().getCodDocIdentificacaoCliente();
        		dataApontamento = tx.getTransacao().getDataApontamento();
        	}
    		
        	// Se o cliente estiver no banco de dados sem status de final de análise, será criado um apontamento para atualização
        	// Caso contrário, será criado um novo apontamento na Base de Dados
        	if(mapaApontamentos.containsKey(entry.getKey())) {
        		apontamento = mapaApontamentos.get(entry.getKey());
        		apontamento.setDataAtualizacaoCalculo(new Timestamp(new Date().getTime()));
        		
        		int total = apontamento.getValorApontamento() + pontuacaoCliente;
        		
        		apontamento.setValorApontamento((short) (
        				apontamento.getValorApontamento() == null ? 0 : (total > 999 ? 999 : total
        					)) );
        	} else {                   		
        		apontamento = new Apontamento(
        			codDocIdentificacaoCliente, 
        			sublote.getCoLote(), 
        			(byte) 2, // Em processamento
        			codTipoIdentificacaoCliente, 
        			dataApontamento, // data do apontamento
        			new Timestamp(new Date().getTime()), // data de atualização do cálculo
        			null, // Se o apontamento não passou do ponto de corte, a carência será nula.
        			null, // flagPontoCorte
        			null, //flagSuspeitoLD
        			pontuacaoCliente > 999 ? 999 : pontuacaoCliente, // valorApontamento
        			(short) valorPontoCorte);
        		apontamento.setNovoApontamento(true);
        	}
        	apontamento.setListaTransacaoAlertada(valor);
        	apontamentos.add(apontamento);
        }
        
    }
    
    public void penalizaClientesApontamentoSequencial(Sublote sublote) throws Exception {
    	
    	// Do Nothing here!
    }
    
    public void executaRegraZero(Sublote sublote) throws Exception {
    	
    	// Do Nothing here!
    }
    
    public void calcularPontoCorte(Sublote sublote, int qtd_threads, long qtdRowsPerPage) throws Exception {
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
    	
    	apontamentosDao.calculaPontoCorte(sublote, loteSimulacao, true);
    }
    
    public static void main(String[] args) {
    	System.out.println(GregorianCalendar.getInstance().get(GregorianCalendar.DAY_OF_MONTH));
	}

    public void push(ObjetoAnalise tx) {
        if (tx != null) {
            transacao.add(tx);
        }
    }

    public boolean hasTransacao() {
    	// Habilitar para debug das regras apontadas...
//    	for(ObjetoAnalise analise : transacao) {
//        	System.out.println("Transação - " + analise.getCodTransacao());
//        	System.out.println("Regras apontadas:");
//        	for (Entry<Short, Boolean> entry : analise.getRegras().entrySet()) {
//                System.out.println(entry.getKey());
//        	}
//    	}
        return transacao.size() > 0;
    }

    public void salvar() throws Exception {
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
        apontamentosDao.gerarApontamentos(apontamentos, loteSimulacao, true);
    }

    public void dispose() {
    	apontamentos.clear();
        transacao.clear();
    }

	public void calculaPontuacaoMensal() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void transporteAgregados(Sublote sublote) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Apontamento> getMapaApontamentos() {
		// TODO Auto-generated method stub
		return null;
	}

}
