package br.com.softon.dtec.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.com.softon.dtec.dao.ApontamentosDAO;
import br.com.softon.dtec.dao.RegrasDAO;
import br.com.softon.dtec.entity.Apontamento;
import br.com.softon.dtec.entity.DetalheApontamento;
import br.com.softon.dtec.entity.ObjetoAnalise;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.entity.Sublote;
import br.com.softon.dtec.entity.TransacaoAlertada;

public class GeradorApontamento implements GeradorAlerta {

    private final Queue<ObjetoAnalise> objetoAnalise = new ConcurrentLinkedQueue<ObjetoAnalise>();
    private final Queue<Apontamento> apontamentos = new ConcurrentLinkedQueue<Apontamento>();
    private final Map<String,Queue<TransacaoAlertada>> mapaClienteTransacoes = new HashMap<String, Queue<TransacaoAlertada>>();
    private final RegraHandle regras;
    
    public GeradorApontamento(final RegraHandle regras) {
        this.regras = regras;
    }
    /**
     * @param regras
     * @throws Exception 
     */
    public void conclusaoAnalise(Sublote sublote) throws Exception {
//    	Habilitar para testes com datas de apontamentos diferentes...
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmm");
    	
    	// Variável para usar nas datas
    	Timestamp ts = new Timestamp(new Date().getTime());
//    	Timestamp ts = new Timestamp(sdf.parse(sublote.getCoLote().toString()).getTime());
    	
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO(); 
    	
        // Variável que conterá todos os apontamentos para o sublote em processamento
    	final Map<String,Apontamento> mapaApontamentos = apontamentosDao.buscarApontamentos(sublote, null, false);
    	
    	// Para evitar NPE, validamos a busca da variável global do ponto de corte...
    	long valorPontoCorte = 
    			(Long) (RegrasDAO.carregarParametrosGlobaisValor() == null ? 0 : 
					RegrasDAO.carregarParametrosGlobaisValor().get("VL_PONTO_CORTE") == null ? 0 :
						RegrasDAO.carregarParametrosGlobaisValor().get("VL_PONTO_CORTE"));
    	
        while (objetoAnalise.size() > 0) {
            ObjetoAnalise objAnalise = objetoAnalise.poll();
            TransacaoAlertada transacaoAlertada = new TransacaoAlertada(objAnalise);
            
            // Será necessário agrupar as transações por cliente para gravar o apontamento...
            // Neste caso, usamos um mapa com a chave sendo uma composição do tipo de documento identificador do cliente e 
            // o número do documento identificador do cliente - o mesmo get usado no redis para trabalhar com o recuo relacionado.
            if(mapaClienteTransacoes.containsKey(objAnalise.getCodigoIdentificadorCliente())) {
            	final Queue<TransacaoAlertada> transacoesAlertadas = mapaClienteTransacoes.get(objAnalise.getCodigoIdentificadorCliente());
            	transacoesAlertadas.add(transacaoAlertada);
            	mapaClienteTransacoes.put(objAnalise.getCodigoIdentificadorCliente(), transacoesAlertadas);
            } else {
                final Queue<TransacaoAlertada> transacoesAlertadas = new ConcurrentLinkedQueue<TransacaoAlertada>();
            	transacoesAlertadas.add(transacaoAlertada);
            	mapaClienteTransacoes.put(objAnalise.getCodigoIdentificadorCliente(), transacoesAlertadas);
            }

            // Como as regras adicionam apenas o código, é necessário buscar o objeto Regra 
            // para pegar o valor de pontuação de cada regra e somar para comparar com o ponto de corte...
            
            //TODO! MUDAR CRIACAO DE REGISTROS DE APONTAMENTO E DETALHE
            for (Entry<Short, Boolean> entry : objAnalise.getRegras().entrySet()) {
                final short coRegra = entry.getKey();
                Regra regra = regras.getRegra(coRegra);
                DetalheApontamento detalheApontamento = new DetalheApontamento(
                		regra.getCodigoClasseRegra(), 
                		objAnalise.getCodDocIdentificacaoCliente(), 
                		sublote.getCoLote(), 
                		regra.getCodigoRegra(), 
                		objAnalise.getCodTipoIdentificacaoCliente(), 
                		regra.getCodigoVersaoSistema(), 
                		objAnalise.getInformacoesPorRegra(coRegra), // TODO - Implementar informações de Análise: informações no formato json com os parâmetros utilizados no apontamento da transação...
                		ts,
                		objAnalise.getDataApontamento(),
                		regra.getValorPontuacao() == 4,
                		(short) regra.getValorPontuacao()
                		);
                transacaoAlertada.adicionarDetalheApontamento(detalheApontamento, 0);
            }
        }
        
        for(Entry<String,Queue<TransacaoAlertada>> entry : mapaClienteTransacoes.entrySet()) {
        	Apontamento apontamento = null;
        	Queue<TransacaoAlertada> valor = entry.getValue();
        	byte codTipoIdentificacaoCliente = 0;
        	String codDocIdentificacaoCliente = null;
        	Timestamp dataApontamento = null;
        	for(TransacaoAlertada tx : valor) {
        		codTipoIdentificacaoCliente = tx.getTransacao().getCodTipoIdentificacaoCliente();
        		codDocIdentificacaoCliente = tx.getTransacao().getCodDocIdentificacaoCliente();
        		dataApontamento = tx.getTransacao().getDataApontamento();
        	}
    		
        	// Se o cliente estiver no banco de dados sem status de final de análise, será criado um apontamento para atualização
        	// Caso contrário, será criado um novo apontamento na Base de Dados
        	if(mapaApontamentos.containsKey(entry.getKey())) {
        		apontamento = mapaApontamentos.get(entry.getKey());    
        	} else {         		          		        		
        		apontamento = new Apontamento(
        			codDocIdentificacaoCliente, 
        			sublote.getCoLote(), 
        			(byte) 2, // Em processamento
        			codTipoIdentificacaoCliente, 
        			dataApontamento, // data do apontamento - Vem direto da regra
        			ts, // data de atualização do cálculo
        			null, // Se o apontamento não passou do ponto de corte, a carência será nula.
        			null, // flagPontoCorte
        			null, //flagSuspeitoLD
        			(short) 0, // valorApontamento
        			(short) valorPontoCorte);
        		apontamento.setNovoApontamento(true);
        	}
        	apontamento.setListaTransacaoAlertada(valor);
        	apontamentos.add(apontamento);
        }
        
    }
    
    public void calculaPontuacaoMensal() throws Exception {
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
    	
    	apontamentosDao.calculaPontuacao();
    }
    
    public void transporteAgregados(Sublote sublote) throws Exception {
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
    	
    	apontamentosDao.transporteAgregados(sublote.getCoLote());
    }
    
    public void calcularPontoCorte(Sublote sublote, int qtd_threads, long qtdRowsPerPage) throws Exception {
//    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
//    	
//    	apontamentosDao.calculaPontoCorte(sublote, null, false);
    	
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
    	
    	final Queue<Integer> listaProcessamento = apontamentosDao.getTotalPaginacao(sublote, null, false, qtdRowsPerPage);
		final List<Future<Integer>> list = new ArrayList<Future<Integer>>();
        
        final Queue<Apontamento> apontamentosAtualizados = new ConcurrentLinkedQueue<Apontamento>();

		final ExecutorService threadPool = Executors
				.newFixedThreadPool(qtd_threads);
		
		while (!listaProcessamento.isEmpty()) {
			final Future<Integer> submit = threadPool
					.submit(new CalculoPCThread(
							apontamentosAtualizados,
							listaProcessamento.poll(),
							qtdRowsPerPage,
							sublote));
			list.add(submit);
		}

		for (final Future<Integer> future : list) {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException(
						"Houve um erro ao processar o sublote "
								+ sublote.getCoSublote()
								+ " do lote " + sublote.getCoLote()
								+ ".", e);
			}
		}

		threadPool.shutdown();
		
        apontamentosDao.concluirAnalise(apontamentosAtualizados, null, false);
    }

    public void push(ObjetoAnalise tx) {
        if (tx != null) {
            objetoAnalise.add(tx);
        }
    }

    public boolean hasTransacao() {
    	// Habilitar para debug das regras apontadas...
//    	for(ObjetoAnalise analise : objetoAnalise) {
//        	System.out.println("Transação - " + analise.getCodTransacao());
//        	System.out.println("Regras apontadas:");
//        	for (Entry<Short, Boolean> entry : analise.getRegras().entrySet()) {
//                System.out.println(entry.getKey());
//        	}
//    	}
        return objetoAnalise.size() > 0;
    }

    public void salvar() throws Exception {
    	ApontamentosDAO apontamentosDao = new ApontamentosDAO();
        apontamentosDao.gerarApontamentos(apontamentos, null, false);
    }

    public void dispose() {
    	apontamentos.clear();
        objetoAnalise.clear();
    }

}
