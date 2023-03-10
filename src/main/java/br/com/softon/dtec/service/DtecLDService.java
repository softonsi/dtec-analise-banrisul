package br.com.softon.dtec.service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.dao.ApontamentosDAO;
import br.com.softon.dtec.dao.ObjetoAnaliseDAO;
import br.com.softon.dtec.dao.RegrasDAO;
import br.com.softon.dtec.dao.SubLoteDAO;
import br.com.softon.dtec.dao.SubLoteDAO.EstadoProcessamento;
import br.com.softon.dtec.entity.Apontamento;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.DetalheApontamento;
import br.com.softon.dtec.entity.ObjetoAnalise;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.entity.Sublote;
import br.com.softon.dtec.entity.TransacaoAlertada;
import br.com.softon.dtec.utils.CloneAvoid;
import br.com.softon.dtec.utils.KettleJobRunner;

public class DtecLDService implements Configuracao {

	private Sublote sublote;
	private RegraHandle regras; 
	private GeradorAlerta geradorApontamento;
	private ControleSimulacao loteSimulacao;

	private final static Logger log = LoggerFactory
			.getLogger(DtecLDService.class);

	public enum ModoExecucao {
		PRODUCAO, TESTE;
	}

	public static void main(final String... args) throws IOException {
		
		boolean isSimulacaoAnalise = false;
		
		if ( args != null && args.length > 0 ) {
			isSimulacaoAnalise = Boolean.parseBoolean(args[0]);
		}
		
		if (isSimulacaoAnalise) {
			CloneAvoid.travarArquivoSistema("../conf/dtecETL", "moduloSimulacao");
			if (CloneAvoid.isLocked()) {
				log.info("Tentativa de in??cio do M??dulo de Simula????o com uma inst??ncia j?? em execu????o.");
				return;
			}
		} else {
			CloneAvoid.travarArquivoSistema("../conf/dtecETL", "moduloAnalise");
			if (CloneAvoid.isLocked()) {
				log.info("Tentativa de in??cio do M??dulo de An??lise com uma inst??ncia j?? em execu????o.");
				return;
			}
		}
		
		new DtecLDService().execute(ModoExecucao.PRODUCAO, isSimulacaoAnalise);
	}
	
	public void execute(final ModoExecucao modo) {
		execute(modo, false);
	}
	
	public void execute(final ModoExecucao modo, boolean isSimulacaoAnalise) {
		log.info("------------------------------M??dulo de An??lise"+(isSimulacaoAnalise ? " (MODO SIMULA????O)" : "")+"------------------------------");
		log.info("--------------------------------Release 1.05.43v4-------------------------------");
		int wait_next_lote_seconds = props_conf.getProperty("wait_next_lote_seconds") == null ? 0 : 
										Integer.parseInt(props_conf.getProperty("wait_next_lote_seconds"));
		
		Object threadsLoad = props_conf.getProperty("qtd_threads");
		int qtd_threads = threadsLoad != null ? Integer.valueOf(threadsLoad.toString()) : 1;
		
		Object objQtdRowsPerPage = props_conf.getProperty("qtd_rows_per_page");
		int qtdRowsPerPage = objQtdRowsPerPage != null ? Integer.valueOf(objQtdRowsPerPage.toString()) : 10000;
		
		resetInformation();
		
		// Vari??vel que contar?? quantas vezes o loop dormiu...
		long count = 0;

		// Toda vez que reiniciar o m??dulo, ser?? verificado se existe algum lote pendente na an??lise...
		checkLoteIncompleto(isSimulacaoAnalise, qtd_threads, qtdRowsPerPage);

		//processa sublotes
		while (true) {
			try {
				checkSimulacao(isSimulacaoAnalise, qtd_threads, qtdRowsPerPage);
				
				sublote = SubLoteDAO.getNextOpenSublote(loteSimulacao, isSimulacaoAnalise);
				if (sublote != null) {

					log.info("Processamento do sublote: "
							+ sublote.getCoSublote() + " do lote " + (sublote.getTipoProcessamento() == 1 ? 
									"di??rio " : sublote.getTipoProcessamento() == 2 ? "mensal " : "")
							+ sublote.getCoLote() + " iniciado...\n");

					if(sublote.getTipoProcessamento() == 2) { // 2 - processamento mensal
						
						boolean desligar_job_kettle = props_conf.getProperty("desligar_job_kettle") == null ? false : 
							props_conf.getProperty("desligar_job_kettle").equalsIgnoreCase("true") ? true : false;
						
						if (!desligar_job_kettle && !isSimulacaoAnalise) {
							log.info("\n\n Executando classifica????o dtec-flex mensal");
							log.info("\n ...");
							KettleJobRunner.runJob();
							log.info("\n Classifica????o dtec-flex mensal finalizada!\n");
						} else {
							log.info("JOB Do KETTLE/DTEC-FLEX desligado");
						}
						
						boolean desligar_transp_hist = props_conf.getProperty("desligar_transp_hist") == null ? false : 
							props_conf.getProperty("desligar_transp_hist").equalsIgnoreCase("true") ? true : false;
						
						if (!desligar_transp_hist && !isSimulacaoAnalise) {
							log.info("\n\n Executando transporte dos apontamentos com status \"0 - Aguardando An??lise\" e \"6 - Parecer da ag??ncia\" para o hist??rico");
							log.info("\n ...");
							ApontamentosDAO apontamentosDao = new ApontamentosDAO();			
							
							// Transporte dos apontamentos com status 0 para hist??rico
					    	apontamentosDao.transporteAguardandoAnalise();
							log.info("\n Transporte dos apontamentos com status \"0 - Aguardando An??lise\" para o hist??rico finalizado!\n");

							// Transporte dos apontamentos com status 6 para hist??rico
//							apontamentosDao.transporteParecerAgencia();
							log.info("\n Transporte dos apontamentos com status \"6 - Parecer da ag??ncia\" para o hist??rico finalizado!\n");
						} else {
							log.info("Transporte dos apontamentos com status \"0 - Aguardando An??lise\" e \"6 - Parecer da ag??ncia\" para o hist??rico desligado");
						}
					}
					
					// Sempre que processar um sublote, a variavel ser?? reiniciada...
					count = 0;
					
					preparaInformacoesSublote(count, isSimulacaoAnalise);

					Processar.counter.set(0);

					long tempo = System.currentTimeMillis();
					
					SortedMap<Short,String> mapaTempoRegra = new ConcurrentSkipListMap<Short,String>();
					
					/* m??todo que executa as queries preparadas para cada regra e retorna as transa????es apontadas para dentro 
					 do gerador de apontamentos... */
					final Queue<Regra> listaProcessamento = getListaRegrasSQL(sublote.getTipoProcessamento()); // Tipo de processamento 1 = Di??rio
					final List<Future<Integer>> list = new ArrayList<Future<Integer>>();

					final ExecutorService threadPool = Executors
							.newFixedThreadPool(qtd_threads);
					
					while (!listaProcessamento.isEmpty()) {
						final Future<Integer> submit = threadPool
								.submit(new Processar(
										mapaTempoRegra,
										listaProcessamento.poll(),
										sublote,
										geradorApontamento, 
										loteSimulacao, 
										isSimulacaoAnalise));
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
					
					for(java.util.Map.Entry<Short, String> entry : mapaTempoRegra.entrySet()){
						log.info("A regra " + entry.getKey() + " demorou " + entry.getValue());
					}

					long tempo2 = System.currentTimeMillis();
					if (geradorApontamento.hasTransacao()) {
						geradorApontamento.conclusaoAnalise(sublote);
						log.info("Houve apontamentos para o sublote: "
							+ sublote.getCoSublote() + " do lote.");
					}
					log.info("Conclus??o da an??lise para o sublote: "
							+ sublote.getCoSublote() + " do lote "
							+ sublote.getCoLote() + " finalizada em "
							+ (System.currentTimeMillis() - tempo2) / 1000
							+ " segundos.");

					long tempo3 = System.currentTimeMillis();
					
					geradorApontamento.salvar();
					
					log.info("Inser????o dos alertas na base de dados para o sublote: "
							+ sublote.getCoSublote()
							+ " do lote "
							+ sublote.getCoLote()
							+ " concluido em "
							+ (System.currentTimeMillis() - tempo3)
							/ 1000
							+ " segundos.");

					SubLoteDAO.atualizarEstado(sublote,
							EstadoProcessamento.SUCESSO, loteSimulacao, isSimulacaoAnalise);
					// Adicionado em 18/01/2012 para atualizar tabela de lotes
					// com a data de fim da an??lise
					// desde que TODOS os sublotes estejam com status "X"...
					Boolean loteCompleto = SubLoteDAO.checkfimLote(sublote.getCoLote(), loteSimulacao, isSimulacaoAnalise);

					log.info("processamento do sublote: "
							+ sublote.getCoSublote() + " do lote "
							+ sublote.getCoLote() + " concluido em "
							+ (System.currentTimeMillis() - tempo) / 1000
							+ " segundos com " + Processar.counter.get()
							+ " disparos.\n");

					if(sublote.getTipoProcessamento() == 2) { // 2 - processamento mensal
						
						boolean desligar_transp_apontamentos_atrasados = props_conf.getProperty("desligar_transp_apontamentos_atrasados") == null ? false : 
							props_conf.getProperty("desligar_transp_apontamentos_atrasados").equalsIgnoreCase("true") ? true : false;
						
						if (!desligar_transp_apontamentos_atrasados && !isSimulacaoAnalise) {
							log.info("\n\n Executando transporte dos apontamentos com status \"2 - Em processamento\" atrasados para o hist??rico");
							log.info("\n Atrasados = Com data de apontamento abaixo da data de processamento do mensal corrente...");
							ApontamentosDAO apontamentosDao = new ApontamentosDAO();					    	
					    	apontamentosDao.transporteApontamentosAtrasados(sublote.getCoLote());
							log.info("\n Transporte dos apontamentos com status \"2 - Em processamento\" atrasados para o hist??rico finalizado!\n");
						} else {
							log.info("Transporte dos apontamentos com status \"2 - Em processamento\" atrasados para o hist??rico desligado");
						}
						
						// TODO - Query para transportar os clientes agregados aos apontamentos anteriores
						log.info("Transportando clientes agregados para o lote mensal: " 
								+ sublote.getCoLote() + "...\n");
						geradorApontamento.transporteAgregados(sublote);
						log.info("Final do transporte dos clientes agregados para o lote mensal: " + sublote.getCoLote()
								+ "...\n");
						
						// TODO - Query para atualizar a pontua????o dos clientes apontados
						log.info("Atualizando a pontua????o dos clientes para o lote mensal: " 
								+ sublote.getCoLote() + "...\n");
						geradorApontamento.calculaPontuacaoMensal();
						log.info("Final da atualiza????o da pontua????o dos clientes para o lote mensal: " + sublote.getCoLote()
								+ "...\n");
						
						log.info("Iniciado c??lculo do ponto de corte para o lote: " + sublote.getCoLote() + "...");
						geradorApontamento.calcularPontoCorte(sublote, qtd_threads, qtdRowsPerPage);						
						log.info("C??lculo do ponto de corte para o lote: " + sublote.getCoLote() + " finalizado.");
						
						// flag do arquivo de propriedades que indicar?? se as regras conclusivas dever??o ser verificadas...
						boolean desligar_regras_conclusivas = props_conf.getProperty("desligar_regras_conclusivas") == null ? false : 
							props_conf.getProperty("desligar_regras_conclusivas").equalsIgnoreCase("true") ? true : false;
						
						if (!desligar_regras_conclusivas && !isSimulacaoAnalise) {
							log.info("Executando regras p??s apontamentos no processamento do lote mensal: " 
									+ sublote.getCoLote() + "...\n");
							regrasPosApontamento();							
							log.info("Final da execu????o das regras p??s apontamentos para o lote mensal: " + sublote.getCoLote()
									+ "...\n");
						} else {
							log.info("Execu????o das regras conclusivas desligado");
						}
					}
					
					// No caso das regras di??rias que processaram, ?? necess??rio executar o ponto de corte...
					if (sublote.getTipoProcessamento() == 1 && isSimulacaoAnalise) {
						log.info("Iniciado c??lculo do ponto de corte da simula????o para o lote: " + sublote.getCoLote() + "...");
						geradorApontamento.calcularPontoCorte(sublote, qtd_threads, qtdRowsPerPage);
					}
					
					if(loteCompleto) {
						log.info("Gravando final do lote: " + sublote.getCoLote() + " na tabela de logs dos processos batch...\n");
						SubLoteDAO.gravaLogProces(sublote.getCoLote(), loteSimulacao, isSimulacaoAnalise);
						
						boolean desligar_log_performance = props_conf.getProperty("desligar_log_performance") == null ? true : 
							props_conf.getProperty("desligar_log_performance").equalsIgnoreCase("true") ? true : false;
						
						if (!desligar_log_performance && !isSimulacaoAnalise) {
							// Adicionado insert na tabela de performance ao final do processamento do lote... (06/03/2018)
							log.info("Gravando final do lote: " + sublote.getCoLote()
									+ " na tabela de log de performance dos processos batch...\n");
							SubLoteDAO.gravaLogPerformance(sublote.getCoLote());
							log.info("Final da grava????o do lote: " + sublote.getCoLote()
									+ " na tabela de log de performance dos processos batch...\n");
						} else {
							log.info("Log de performance desligado");
						}
						
						log.info("Gravando final do lote: " + sublote.getCoLote() + " na tabela de lotes dos processos batch...\n");
						SubLoteDAO.fimLote(sublote.getCoLote(), loteSimulacao, isSimulacaoAnalise);
						
						boolean desligar_criacao_lote_mensal = props_conf.getProperty("desligar_criacao_lote_mensal") == null ? true : 
							props_conf.getProperty("desligar_criacao_lote_mensal").equalsIgnoreCase("true") ? true : false;
						
						if (!desligar_criacao_lote_mensal && 
								!isSimulacaoAnalise && 
								GregorianCalendar.getInstance().get(GregorianCalendar.DAY_OF_MONTH) >= 5) {
							// Rotina de verifica????o de an??lise mensal para criar lote mensal de radical AAAAMM e 1 sublote, 
							// a fim do pr??ximo loop pegar este sublote novo...
							Integer loteMensal = SubLoteDAO.getLoteMensal();
							if (loteMensal != 0) {
								// Para verificar o risco, ?? necess??rio adicionar 1 m??s ao lote...
								GregorianCalendar gc = new GregorianCalendar();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
								gc.setTime(sdf.parse(loteMensal+""));
								gc.add(GregorianCalendar.MONTH, 1);
								if (SubLoteDAO.confereLoteRisco( Integer.parseInt( sdf.format( gc.getTime() ) ) ) ) {
									log.info("Criando lote mensal " + loteMensal + " nas tabelas de lotes e sublotes dos processos batch...\n");
									SubLoteDAO.criarLoteMensal(loteMensal);
									SubLoteDAO.criarSubloteMensal(loteMensal);
									log.info("Lote mensal " + loteMensal + " criado nas tabelas de lotes e sublotes dos processos batch...\n");
								} else {
									log.info("Cria????o do lote mensal " + loteMensal + 
											" nas tabelas de lotes e sublotes dos processos batch n??o p??de ser feito porque o processo CR e PM para este m??s ainda n??o foi executado...\n");
									log.info("Cria????o do lote mensal " + loteMensal + 
											" portanto ficar?? pendente para o final da execu????o do pr??ximo lote di??rio.\n");
								}
							} 
						} else {
							if(desligar_criacao_lote_mensal) log.info("Cria????o de lote mensal desligado.");
							if(GregorianCalendar.getInstance().get(GregorianCalendar.DAY_OF_MONTH) < 5) log.info("Data atual est?? entre o primeiro e o quarto dia ??til do m??s.");
						}
						
					}

					if(sublote.getTipoProcessamento() == 2) { // 2 - processamento mensal
						// No final de tudo, depois que o mensal terminou e fechou seu ciclo, vamos fazer um reorg na Base...
						
						boolean desligar_reorg = props_conf.getProperty("desligar_reorg") == null ? false : 
							props_conf.getProperty("desligar_reorg").equalsIgnoreCase("true") ? true : false;
						
						if (!desligar_reorg && !isSimulacaoAnalise) {
							log.info("Executando REORG no BANCO DE DADOS ap??s o processamento do mensal...\n");
							SubLoteDAO.executaREORG(sublote.getCoLote());
							log.info("REORG no BANCO DE DADOS executado com sucesso...\n");
						} else {
							log.info("REORG desligado");
						}
						
					}
					
					resetInformation();

				} else {
					if (modo == ModoExecucao.TESTE) {
						log.info("Sem sublotes para processar...");
						return;
					}
					if (count > 5 * 60) {
						log.info("Sem sub lote para processar.\n");
						// Zerar contador para a pr??xima mensagem de log...
						count = 0;
					}

					if (geradorApontamento != null) {
						geradorApontamento.dispose();
						geradorApontamento = null;
					}
					Thread.sleep(wait_next_lote_seconds);
					// Sempre que dormir, a variavel ser?? incrementada...
					count++;
				}
			} catch (ExecutionException e) {
				log.error(
						"Erro 003 - Problemas durante a execu????o em Threads do sublote: "
								+ sublote.getCoSublote() + " do lote "
								+ sublote.getCoLote() + ".", e);
				log.error("\n\n");
				System.exit(3);
			} catch (RuntimeException e) {
				log.error(
						"Erro 004 - Problemas durante o processamento do sublote: "
								+ sublote.getCoSublote() + " do lote "
								+ sublote.getCoLote() + ".", e);
				log.error("\n\n");
				try {
					SubLoteDAO.atualizarEstado(sublote,
							EstadoProcessamento.ERRO, loteSimulacao, isSimulacaoAnalise);
				} catch (SQLException erro) {
					log.error("Erro 002 - Problemas com requisi????es no banco de dados...");
					while (erro != null) {
						log.error("SQLState.........: " + erro.getSQLState());
						log.error("C??digo de Erro...: " + erro.getErrorCode());
						log.error("Descri????o do Erro: " + erro.getMessage(),
								erro);
						erro = erro.getNextException();
					}
					System.exit(2);
				} catch (Exception e1) {
					log.error("Erro 005 - Erro desconhecido.", e1);
					System.exit(5);
				}
				System.exit(4);
			} catch (SQLException e) {
				log.error("Erro 002 - Problemas com requisi????es no banco de dados...");
				while (e != null) {
					log.error("SQLState.........: " + e.getSQLState());
					log.error("C??digo de Erro...: " + e.getErrorCode());
					log.error("Descri????o do Erro: " + e.getMessage(), e);
					e = e.getNextException();
				}
				System.exit(2);
			} catch (Exception e) {
				log.error("Erro 005 - Erro desconhecido.", e);
				try {
					SubLoteDAO.atualizarEstado(sublote,
							EstadoProcessamento.ERRO, loteSimulacao, isSimulacaoAnalise);
				} catch (SQLException erro) {
					log.error("Erro 002 - Problemas com requisi????es no banco de dados...");
					while (erro != null) {
						log.error("SQLState.........: " + erro.getSQLState());
						log.error("C??digo de Erro...: " + erro.getErrorCode());
						log.error("Descri????o do Erro: " + erro.getMessage(),
								erro);
						erro = erro.getNextException();
					}
					System.exit(2);
				} catch (Exception e1) {
					log.error("Erro 005 - Erro desconhecido.", e1);
					System.exit(5);
				}
				System.exit(5);
			} finally {
				
			}
		}
	}

	/**
	 * Verifica se existem lotes pendentes para finalizar o m??dulo de an??lise antes de pegar o pr??ximo.
	 * @param isSimulacaoAnalise
	 */
	private void checkLoteIncompleto(boolean isSimulacaoAnalise, int qtd_threads, long qtdRowsPerPage) {
		try {
			if (isSimulacaoAnalise && loteSimulacao == null) {
				loteSimulacao = SubLoteDAO.getLoteSimulacao();
			}
			// Verifica????o adicionada para concluir c??lculo do ponto de corte caso houve erro no c??lculo anterior...
			// O sistema n??o avan??ar?? enquanto n??o resolver o status pendente (status = 70 ou 80)
			do {
				sublote = SubLoteDAO.checkLoteIncompleto(isSimulacaoAnalise);
				
				if(sublote != null) {
					if (sublote.getTipoProcessamento() == 2) {
						log.info("Iniciado c??lculo do ponto de corte para o lote: " + sublote.getCoLote() + "...");
						getGeradorApontamentos(isSimulacaoAnalise).calcularPontoCorte(sublote, qtd_threads, qtdRowsPerPage);
					}
					log.info("Gravando final do lote: " + sublote.getCoLote()
					+ " na tabela de lotes dos processos batch...\n");
					SubLoteDAO.fimLote(sublote.getCoLote(), loteSimulacao, isSimulacaoAnalise);
					log.info("Gravando final do lote: " + sublote.getCoLote()
							+ " na tabela de logs dos processos batch...\n");
					SubLoteDAO.gravaLogProces(sublote.getCoLote(), loteSimulacao, isSimulacaoAnalise);
				}
			} while (sublote != null);
		} catch (SQLException e) {
			log.error("Erro 002 - Problemas com requisi????es no banco de dados...");
			while (e != null) {
				log.error("SQLState.........: " + e.getSQLState());
				log.error("C??digo de Erro...: " + e.getErrorCode());
				log.error("Descri????o do Erro: " + e.getMessage(), e);
				e = e.getNextException();
			}
			System.exit(2);
		} catch (Exception e) {
			log.error("Erro 005 - Erro desconhecido.", e);
			System.exit(5);
		}
	}

	private void checkSimulacao(boolean isSimulacaoAnalise, int qtd_threads, long qtdRowsPerPage) throws SQLException, Exception {
		if (isSimulacaoAnalise && loteSimulacao == null) {
			loteSimulacao = SubLoteDAO.getLoteSimulacao();
		}		

		// Toda vez que pegar um sublote, ser?? verificado se existe algum lote pendente na an??lise...
		checkLoteIncompleto(isSimulacaoAnalise, qtd_threads, qtdRowsPerPage);
	}

	private void resetInformation() {
		if(sublote != null) sublote = null;
		if(geradorApontamento != null) geradorApontamento = null;
		if(regras != null) regras = null;
		if(loteSimulacao != null) loteSimulacao = null;
	}

	private void preparaInformacoesSublote(long count, boolean isSimulacaoAnalise) throws SQLException, Exception {
				
		regras = RegrasDAO.carregarRegras(isSimulacaoAnalise, loteSimulacao);
	    
		//GERAR APONTADOR
		if (geradorApontamento != null) {
			geradorApontamento.dispose();
			geradorApontamento = null;
		}
		
		geradorApontamento = getGeradorApontamentos(isSimulacaoAnalise);
	}

	private GeradorAlerta getGeradorApontamentos(boolean isSimulacaoAnalise) {
		if ( !isSimulacaoAnalise ) {
			return new GeradorApontamento(regras);
		} else {
			return new GeradorApontamentoSimulacao(regras, loteSimulacao);
		}		
	}

	private synchronized Queue<Regra> getListaRegrasSQL(Byte tipoProcesso) {
		Set<Regra> value = new LinkedHashSet<Regra>();	
		
		List<Regra> listaRegras = tipoProcesso == 1 ? 
				regras.getRegrasProcessamentoDiario() : tipoProcesso == 2 ? regras.getRegrasProcessamentoMensal() : regras.getTodasRegras();

		for (Regra r : listaRegras) {
			
			if (r.getCodigoRegra() == 0) {
//				log.warn("Regra ZERO n??o ser?? processada junto com as demais!");
				continue;
			}

			if (r.getTextoRegraDinamica() == null || r.getTextoRegraDinamica().trim().length() == 0) {
				log.warn("O SQL da regra '" + r.getCodigoRegra() + "' nao possui conteudo.");
				continue;
			}

			if (!r.getTextoRegraDinamica().contains("SELECT")) {
				log.warn("O SQL da regra '" + r.getCodigoRegra() + "' nao possui script SQL.");
				continue;
			}

			value.add(r);
		}	

		return new ConcurrentLinkedQueue<Regra>(value);
	}
	
	/** @throws Exception 
	 * @throws SQLException 
	 **/
	private void regrasPosApontamento() throws SQLException, Exception {

    	ApontamentosDAO apontamentosDao = new ApontamentosDAO(); 
    	final Map<String,Apontamento> mapaApontamentos = apontamentosDao.buscarApontamentos(sublote, null, false);
		
		List<Regra> regrasPosProcessamento = regras.getRegrasPosProcessamentoMensal();
		
		if(regrasPosProcessamento == null || regrasPosProcessamento.isEmpty()) {
			log.warn("Nenhuma regra p??s processamento habilitada para execu????o.");
		}
		
		for (Regra regra : regrasPosProcessamento) {
			long tempo = System.currentTimeMillis();
			log.debug("processamento do SQL da regra: " + regra.getCodigoRegra() + " iniciado...");
			List<ObjetoAnalise> listaObj = ObjetoAnaliseDAO.getObjetosPorRegra(regra, sublote, null, false);
			List<DetalheApontamento> listaDetalhesApontamentos = new ArrayList<DetalheApontamento>();
			final Queue<Apontamento> apontamentos = new ConcurrentLinkedQueue<Apontamento>();
			for (ObjetoAnalise obj : listaObj) {
				Apontamento apontamento = null;

				short valor = obj.getValorPenalizacao();
				
				if (mapaApontamentos.containsKey(obj.getCodigoIdentificadorCliente())) {
					apontamento = mapaApontamentos.get(obj.getCodigoIdentificadorCliente());

					if (apontamento.getCodigoStatusEventoAtual() == 0) {
						DetalheApontamento detalheApontamento = new DetalheApontamento(regra.getCodigoClasseRegra(),
								obj.getCodDocIdentificacaoCliente(), sublote.getCoLote(), regra.getCodigoRegra(),
								obj.getCodTipoIdentificacaoCliente(), regra.getCodigoVersaoSistema(),
								obj.getInformacoesPorRegra(regra.getCodigoRegra()), // TODO - Implementar informa????es de An??lise: informa????es no formato json com os par??metros utilizados no apontamento da transa????o...
								new Timestamp(new Date().getTime()), obj.getDataApontamento(),
								regra.getValorPontuacao() == 4, valor > 999 ? 999 : valor);
						listaDetalhesApontamentos.add(detalheApontamento);
						TransacaoAlertada transacaoAlertada = new TransacaoAlertada(obj);
						transacaoAlertada.adicionarDetalheApontamento(detalheApontamento, 0);
						apontamento.setValorApontamento((short) ((apontamento.getValorApontamento() + valor) > 999 ? 999
								: (apontamento.getValorApontamento() + valor)));
						final Queue<TransacaoAlertada> transacoesAlertadas = new ConcurrentLinkedQueue<TransacaoAlertada>();
						transacoesAlertadas.add(transacaoAlertada);
						apontamento.setListaTransacaoAlertada(transacoesAlertadas);
						apontamentos.add(apontamento);
					}
				}
			}
			ApontamentosDAO apontamentosDAO = new ApontamentosDAO();
			apontamentosDAO.gerarApontamentos(apontamentos, null, false);
			tempo = System.currentTimeMillis() - tempo;
			log.debug("processamento do SQL da regra: " + regra.getCodigoRegra() + " concluido em " + tempo / 1000
					+ " segundos com " + listaObj.size() + " transa????es processadas.");
		}
		
	}

}
