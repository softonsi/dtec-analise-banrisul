package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.APONTAMENTOS;

import java.io.StringReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.Apontamento;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.DetalheApontamento;
import br.com.softon.dtec.entity.Sublote;
import br.com.softon.dtec.entity.TransacaoAlertada;

public class ApontamentosDAO {

    private final static Logger log = LoggerFactory.getLogger(ApontamentosDAO.class);

    private static final String INSERT_DETALHE_APONTAMENTO = carregarQuery(APONTAMENTOS, "insert_detalhe_apontamento.sql");
    private static final String INSERT_DETALHE_APONTAMENTO_SIMULA = carregarQuery(APONTAMENTOS, "insert_detalhe_apontamento_simula.sql");
    private static final String INSERT_EVENTO = carregarQuery(APONTAMENTOS, "insert_evento.sql");
    private static final String CALCULA_PONTUACAO = carregarQuery(APONTAMENTOS, "calcula_pontuacao.sql");
    private static final String CALCULA_PONTO_CORTE = carregarQuery(APONTAMENTOS, "calcula_ponto_corte.sql");
    private static final String CALCULA_PONTO_CORTE_SIMULA = carregarQuery(APONTAMENTOS, "calcula_ponto_corte_simula.sql");
    private static final String TRANSPORTE_AGREGADOS = carregarQuery(APONTAMENTOS, "transporte_agregados.sql");
    private static final String TRANSPORTE_AGUARDANDO_ANALISE = carregarQuery(APONTAMENTOS, "transporte_aguardando_analise.sql");
    private static final String TRANSPORTE_PARECER_AGENCIA = carregarQuery(APONTAMENTOS, "transporte_parecer_agencia.sql");
    private static final String TRANSPORTE_APONTAMENTOS_ATRASADOS = carregarQuery(APONTAMENTOS, "transporte_apontamentos_atrasados.sql");
    private static final String INSERT_APONTAMENTOS = carregarQuery(APONTAMENTOS, "insert_apontamentos.sql");
    private static final String INSERT_APONTAMENTOS_SIMULA = carregarQuery(APONTAMENTOS, "insert_apontamentos_simula.sql");
    private static final String UPDATE_APONTAMENTOS = carregarQuery(APONTAMENTOS, "update_apontamentos.sql");
    private static final String UPDATE_APONTAMENTOS_SIMULA = carregarQuery(APONTAMENTOS, "update_apontamentos_simula.sql");
    private static final String UPDATE_APONTAMENTOS_CONCLUSAO_ANALISE = carregarQuery(APONTAMENTOS, "update_apontamentos_conclusao_analise.sql");
    private static final String UPDATE_APONTAMENTOS_CONCLUSAO_ANALISE_SIMULA = carregarQuery(APONTAMENTOS, "update_apontamentos_conclusao_analise_simula.sql");
    private static final String SELECT_APONTAMENTOS = carregarQuery(APONTAMENTOS, "select_apontamentos.sql");
    private static final String SELECT_APONTAMENTOS_SIMULA = carregarQuery(APONTAMENTOS, "select_apontamentos_simula.sql");
    private static final String SELECT_TOTAL_APONTAMENTOS_LOTE = carregarQuery(APONTAMENTOS, "select_total_apontamentos_lote.sql");
    private static final String SELECT_TOTAL_APONTAMENTOS_LOTE_SIMULA = carregarQuery(APONTAMENTOS, "select_total_apontamentos_lote_simula.sql");
    private static final String SELECT_APONTAMENTOS_LOTE_MENSAL = carregarQuery(APONTAMENTOS, "select_apontamentos_lote_mensal.sql");
    private static final String SELECT_APONTAMENTOS_LOTE_SIMULA_MENSAL = carregarQuery(APONTAMENTOS, "select_apontamentos_lote_simula_mensal.sql");
	private static final String SELECT_REGRAS_ACUMULATIVAS = carregarQuery(APONTAMENTOS, "select_regras_acumulativas.sql");
	private static final String SELECT_REGRAS_ACUMULATIVAS_SIMULA = carregarQuery(APONTAMENTOS, "select_regras_acumulativas_simula.sql");
	private static final String SELECT_REGRAS_APONTAMENTOS_ANTERIORES = carregarQuery(APONTAMENTOS, "select_regras_apontamentos_anteriores.sql");
	private static final String SELECT_REGRAS_APONTAMENTO_CORRENTE = carregarQuery(APONTAMENTOS, "select_regras_apontamento_corrente.sql");
	private static final String EXISTE_REGRA_GRAVISSIMA = carregarQuery(APONTAMENTOS, "existe_regra_gravissima.sql");
	private static final String EXISTE_REGRA_GRAVISSIMA_SIMULA = carregarQuery(APONTAMENTOS, "existe_regra_gravissima_simula.sql");
	
	
    public Map<String,Apontamento> buscarApontamentos(Sublote sublote, ControleSimulacao loteSimulacaoAnalise, boolean isSimulacao) throws Exception {
    	Map<String,Apontamento> mapa = new HashMap<String,Apontamento>();
    	Apontamento apontamento = null;
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

            final PreparedStatement ps = con.prepareStatement(buildSelectApontamentos(loteSimulacaoAnalise, isSimulacao));
            
            if (isSimulacao) {
	            ps.setLong(1, loteSimulacaoAnalise.getCodIdentificadorSimulacao());
            }

            rs = ps.executeQuery();

            while (rs.next()) {
            	apontamento = buildApontamento(loteSimulacaoAnalise, isSimulacao, rs);
            	StringBuilder chave = new StringBuilder();
            	chave.append(rs.getString("DT_APONTAMENTO") != null ? rs.getString("DT_APONTAMENTO").substring(0, 10) : null);
            	chave.append(rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getString("CD_TP_IDENTF_CLIE") : null);
            	chave.append(rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null);
            	mapa.put(
            			chave.toString(),
            			apontamento);
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	
    	return mapa;
    }

    private Apontamento buildApontamento(ControleSimulacao loteSimulacaoAnalise, boolean isSimulacao, ResultSet rs) throws SQLException {
    	if (!isSimulacao) {
    		return new Apontamento(
	            	rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null,
					rs.getString("CD_LOTE") != null ? rs.getLong("CD_LOTE") : null,
					rs.getString("CD_STTUS_EVENTO_ATUAL") != null ? rs.getByte("CD_STTUS_EVENTO_ATUAL") : null,
					rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getByte("CD_TP_IDENTF_CLIE") : null,
					rs.getString("DT_APONTAMENTO") != null ? rs.getTimestamp("DT_APONTAMENTO") : null,
					rs.getString("DT_ATUALZ_CALCULO") != null ? rs.getTimestamp("DT_ATUALZ_CALCULO") : null,
					rs.getString("FL_CARENCIA") != null ? rs.getByte("FL_CARENCIA") : null,
					rs.getString("FL_PONTO_CORTE") != null ? rs.getByte("FL_PONTO_CORTE") : null,
					rs.getString("FL_SUSP_LD") != null ? rs.getByte("FL_SUSP_LD") : null,
					rs.getString("VL_APONTAMENTO") != null ? rs.getShort("VL_APONTAMENTO") : null,
					rs.getString("VL_PONTO_CORTE") != null ? rs.getShort("VL_PONTO_CORTE") : null
				);
		}
    	
    	return new Apontamento(
    				rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null,
					rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getByte("CD_TP_IDENTF_CLIE") : null,
					rs.getString("DT_APONTAMENTO") != null ? rs.getTimestamp("DT_APONTAMENTO") : null,
					rs.getString("VL_APONTAMENTO") != null ? rs.getShort("VL_APONTAMENTO") : null,
					rs.getString("VL_PONTO_CORTE") != null ? rs.getShort("VL_PONTO_CORTE") : null,
					rs.getString("FL_SUSP_LD") != null ? rs.getByte("FL_SUSP_LD") : null,
					rs.getString("CD_LOTE") != null ? rs.getLong("CD_LOTE") : null,
					rs.getString("CD_IDENTF_SIMULA") != null ? rs.getLong("CD_IDENTF_SIMULA") : null
    			);
	}

	private static String buildSelectApontamentos(ControleSimulacao loteSimulacaoAnalise, boolean isSimulacao) {
    	if (!isSimulacao) {
			return SELECT_APONTAMENTOS;
		}
    	
    	return SELECT_APONTAMENTOS_SIMULA;
	}

	public void gerarApontamentos(final Queue<Apontamento> apontamentos, ControleSimulacao loteSimulacao, boolean isSimulacao) throws SQLException, Exception {
        Connection con = null;
        
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            
            // TODO - Insert na TB_APONTAMENTO e na TB_DETLH_APONTAMENTO
            final PreparedStatement psInsertApontamento = con.prepareStatement(buildQueryInsertApontamento(isSimulacao));
            final PreparedStatement psUpdateApontamento = con.prepareStatement(buildQueryUpdateApontamento(isSimulacao));
            final PreparedStatement psDetalheApontamento = con.prepareStatement(buildQueryInsertDetalhe(isSimulacao));
            
            //TODO! CONTROLAR COLUNAS DO INSERT PARA O CASO DE SIMULAÇÃO
            boolean batchApontamentosExecutado = false;
            boolean batchDetalhesExecutado = false;
            
            long acumQtApontamentosInseridos = 0;
            long acumQtApontamentosAtualizados = 0;
            long acumQtDetalhesInseridos = 0;
            
        	//controla batch de inserção de detalhes
        	long totalBatchDetalhes = 0;
            
            for (Apontamento apontamento : apontamentos) {
            	batchApontamentosExecutado = false;
            	
            	//controla quando inserir apontamentos antes dos detalhes
            	
            	if(apontamento.getNovoApontamento()) {
            		prepareNewInsertApontamento(psInsertApontamento, apontamento, isSimulacao, loteSimulacao);
            	} else {
                    prepareUpdateApontamento(psUpdateApontamento, apontamento, isSimulacao, loteSimulacao);
            	}
            	
            	for(TransacaoAlertada transacaoAlertada : apontamento.getListaTransacaoAlertada()) {
            		
                	for(DetalheApontamento alerta : transacaoAlertada.getListaDetalheApontamentos()) {
                		prepareInsertDetalhe(psDetalheApontamento, alerta, apontamento, isSimulacao, loteSimulacao);
                		
                		++totalBatchDetalhes;
                		
                		//se o tamanho do batch for igual ao configurado posteriormente...
                		if(totalBatchDetalhes == ConnectionPool.BATCH_SIZE) {
                			if(!batchApontamentosExecutado) {
	                            int[] first = psInsertApontamento.executeBatch();
	                            
	                            acumQtApontamentosInseridos += first.length;
	                            
	                            int[] second = psUpdateApontamento.executeBatch();

	                            acumQtApontamentosAtualizados += second.length;
	                            
	                            batchApontamentosExecutado = true;
                			}
                			
                			int[] third = psDetalheApontamento.executeBatch();
                			acumQtDetalhesInseridos += third.length;
                            
                            totalBatchDetalhes = 0;
                            batchDetalhesExecutado = true;

                            log.info("Inserindo detalhes confome BATCH-SIZE configurado - "+ConnectionPool.BATCH_SIZE);
                		} else {
                			batchDetalhesExecutado = false;
                		}
                	}     
                	
            	}
            }
            
			if(!batchApontamentosExecutado) {
				int[] first = psInsertApontamento.executeBatch();
				acumQtApontamentosInseridos += first.length;
				log.info("Inserindo últimos apontamentos...");
				int[] second = psUpdateApontamento.executeBatch();
				acumQtApontamentosAtualizados += second.length;
				log.info("Atualizando últimos apontamentos...");
			}
			if(!batchDetalhesExecutado) {
				int[] third = psDetalheApontamento.executeBatch();
				acumQtDetalhesInseridos += third.length;
				log.info("Inserindo últimos detalhes...");
			}
			
			log.info("Quantidade de apontamentos inseridos - " + acumQtApontamentosInseridos);
			log.info("Quantidade de apontamentos atualizados - " + acumQtApontamentosAtualizados);
			log.info("Quantidade de detalhes de apontamentos inseridos - " + acumQtDetalhesInseridos);
            
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Houve um erro ao inserir os dados de Apontamentos...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao inserir os dados de Apontamentos...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();               
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }

    }

	private void prepareInsertDetalhe(PreparedStatement psDetalheApontamento, DetalheApontamento alerta,
			Apontamento apontamento, boolean isSimulacao, ControleSimulacao loteSimulacao) throws SQLException {
		if (!isSimulacao) {
	        if (alerta.getCodigoClasseRegra() == null) {
	        	psDetalheApontamento.setNull(1, Types.NULL);
	        } else {
	    		psDetalheApontamento.setShort(1, alerta.getCodigoClasseRegra());
	        }
	
	        if (apontamento.getCodDocIdentificacaoCliente() == null) {
	        	psDetalheApontamento.setNull(2, Types.NULL);
	        } else {
	    		psDetalheApontamento.setString(2, apontamento.getCodDocIdentificacaoCliente());
	        }
	
	        if (alerta.getCodigoLote() == null) {
	        	psDetalheApontamento.setNull(3, Types.NULL);
	        } else {
	    		psDetalheApontamento.setLong(3, alerta.getCodigoLote());
	        }
	
	        if (alerta.getCodigoRegra() == null) {
	        	psDetalheApontamento.setNull(4, Types.NULL);
	        } else {
	    		psDetalheApontamento.setInt(4, alerta.getCodigoRegra());
	        }
	
	        if (apontamento.getCodTipoIdentificacaoCliente() == null) {
	        	psDetalheApontamento.setNull(5, Types.NULL);
	        } else {
	    		psDetalheApontamento.setByte(5, apontamento.getCodTipoIdentificacaoCliente());
	        }
	
	        if (alerta.getCodigoVersaoSistema() == null) {
	        	psDetalheApontamento.setNull(6, Types.NULL);
	        } else {
	    		psDetalheApontamento.setByte(6, alerta.getCodigoVersaoSistema());
	        }
	
	        if (alerta.getDescricaoInformacoesAnalise() == null) {
	        	// Modificado para colocar string vazia no lugar de nulo (data - 26/05/2017) 
	        	// para evitar erro "Operand type clash: varbinary is incompatible with ntext"
	        	psDetalheApontamento.setString(7, "");
	        } else {
	        	// Modificado para colocar campo tipo Clob (data - 30/10/2020) 
	        	// para evitar erro "ORA-03106: erro fatal de protocolo de comunicação de duas tarefas"
	        	// fonte de pesquisa - https://stackoverflow.com/questions/49908847/ora-03106-fatal-two-task-communication-protocol-error-in-batch-update
	        	// após implementar a dica de cima, houve erro com o clob, então usei essa dica - https://stackoverflow.com/questions/14295506/using-clob-in-java-throwing-exception
	        	StringReader reader = new StringReader(alerta.getDescricaoInformacoesAnalise());
	        	psDetalheApontamento.setCharacterStream(7, reader, alerta.getDescricaoInformacoesAnalise().length());
	        }
	
	        if (apontamento.getDataApontamento() == null) {
	        	psDetalheApontamento.setNull(8, Types.NULL);
	        } else {
	    		psDetalheApontamento.setTimestamp(8, apontamento.getDataApontamento());
	        }
	
	        if (alerta.getDataDisparoRegra() == null) {
	        	psDetalheApontamento.setNull(9, Types.NULL);
	        } else {
	    		psDetalheApontamento.setTimestamp(9, alerta.getDataDisparoRegra());
	        }
	
	        if (alerta.getVlPonto() == null) {
	        	psDetalheApontamento.setNull(10, Types.NULL);
	        } else {
	    		psDetalheApontamento.setShort(10, alerta.getVlPonto());
	        }
	
	        if (apontamento.getCodigoLote() == null) {
	        	psDetalheApontamento.setNull(11, Types.NULL);
	        } else {
	    		psDetalheApontamento.setLong(11, apontamento.getCodigoLote());
	        }
		} else {
			if (alerta.getCodigoClasseRegra() == null) {
				psDetalheApontamento.setNull(1, Types.NULL);
			} else {
				psDetalheApontamento.setShort(1, alerta.getCodigoClasseRegra());
			}
			
			if (apontamento.getCodDocIdentificacaoCliente() == null) {
				psDetalheApontamento.setNull(2, Types.NULL);
			} else {
				psDetalheApontamento.setString(2, apontamento.getCodDocIdentificacaoCliente());
			}
			
			if (alerta.getCodigoLote() == null) {
				psDetalheApontamento.setNull(3, Types.NULL);
			} else {
				psDetalheApontamento.setLong(3, alerta.getCodigoLote());
			}
			
			if (alerta.getCodigoRegra() == null) {
				psDetalheApontamento.setNull(4, Types.NULL);
			} else {
				psDetalheApontamento.setInt(4, alerta.getCodigoRegra());
			}
			
			if (apontamento.getCodTipoIdentificacaoCliente() == null) {
				psDetalheApontamento.setNull(5, Types.NULL);
			} else {
				psDetalheApontamento.setByte(5, apontamento.getCodTipoIdentificacaoCliente());
			}
			
			if (alerta.getCodigoVersaoSistema() == null) {
				psDetalheApontamento.setNull(6, Types.NULL);
			} else {
				psDetalheApontamento.setByte(6, alerta.getCodigoVersaoSistema());
			}
	
	        if (alerta.getDescricaoInformacoesAnalise() == null) {
	        	// Modificado para colocar string vazia no lugar de nulo (data - 26/05/2017) 
	        	// para evitar erro "Operand type clash: varbinary is incompatible with ntext"
	        	psDetalheApontamento.setString(7, "");
	        } else {
	        	// Modificado para colocar campo tipo Clob (data - 30/10/2020) 
	        	// para evitar erro "ORA-03106: erro fatal de protocolo de comunicação de duas tarefas"
	        	// fonte de pesquisa - https://stackoverflow.com/questions/49908847/ora-03106-fatal-two-task-communication-protocol-error-in-batch-update
	        	// após implementar a dica de cima, houve erro com o clob, então usei essa dica - https://stackoverflow.com/questions/14295506/using-clob-in-java-throwing-exception
	        	StringReader reader = new StringReader(alerta.getDescricaoInformacoesAnalise());
	        	psDetalheApontamento.setCharacterStream(7, reader, alerta.getDescricaoInformacoesAnalise().length());
	        }
			
			psDetalheApontamento.setLong(8, loteSimulacao.getCodIdentificadorSimulacao());
			
			if ( apontamento.getDataApontamento() == null ) {
				//TODO! CHECK NULO
				psDetalheApontamento.setTimestamp(9, new Timestamp(new Date().getTime()));
			} else {
				psDetalheApontamento.setTimestamp(9, apontamento.getDataApontamento());
			}
		}
		
		psDetalheApontamento.addBatch();		
	}

	private void prepareUpdateApontamento(
			PreparedStatement psUpdateApontamento, Apontamento apontamento, boolean isSimulacao, ControleSimulacao loteSimulacao) throws SQLException {
		
		if (!isSimulacao) {
			if (apontamento.getDataAtualizacaoCalculo() == null) {
	        	psUpdateApontamento.setNull(1, Types.NULL);
	        } else {
	    		psUpdateApontamento.setTimestamp(1, apontamento.getDataAtualizacaoCalculo());
	        }
	
	        if (apontamento.getValorApontamento() == null) {
	        	psUpdateApontamento.setNull(2, Types.NULL);
	        } else {
	    		psUpdateApontamento.setShort(2, apontamento.getValorApontamento());
	        }
	
	        if (apontamento.getFlagSuspeitoLD() == null) {
	        	psUpdateApontamento.setNull(3, Types.NULL);
	        } else {
	    		psUpdateApontamento.setByte(3, apontamento.getFlagSuspeitoLD());
	        }
	
	        if (apontamento.getDataApontamento() == null) {
	        	psUpdateApontamento.setNull(4, Types.NULL);
	        } else {
	    		psUpdateApontamento.setTimestamp(4, apontamento.getDataApontamento());
	        }
	
	        if (apontamento.getCodTipoIdentificacaoCliente() == null) {
	        	psUpdateApontamento.setNull(5, Types.NULL);
	        } else {
	    		psUpdateApontamento.setByte(5, apontamento.getCodTipoIdentificacaoCliente());
	        }
	
	        if (apontamento.getCodDocIdentificacaoCliente() == null) {
	        	psUpdateApontamento.setNull(6, Types.NULL);
	        } else {
	    		psUpdateApontamento.setString(6, apontamento.getCodDocIdentificacaoCliente());
	        }
	
	        if (apontamento.getCodigoLote() == null) {
	        	psUpdateApontamento.setNull(7, Types.NULL);
	        } else {
	    		psUpdateApontamento.setLong(7, apontamento.getCodigoLote());
	        }
		} else {
			if (apontamento.getValorPontoCorte() == null) {
				psUpdateApontamento.setNull(1, Types.NULL);
			} else {
				psUpdateApontamento.setShort(1, apontamento.getValorApontamento());
			}
			
			if (apontamento.getValorApontamento() == null) {
				psUpdateApontamento.setNull(2, Types.NULL);
			} else {
				psUpdateApontamento.setShort(2, apontamento.getValorApontamento());
			}
			
			if (apontamento.getFlagSuspeitoLD() == null) {
				psUpdateApontamento.setNull(3, Types.NULL);
			} else {
				psUpdateApontamento.setByte(3, apontamento.getFlagSuspeitoLD());
			}
			
			psUpdateApontamento.setLong(4, loteSimulacao.getCodIdentificadorSimulacao());
			
			if (apontamento.getCodTipoIdentificacaoCliente() == null) {
				psUpdateApontamento.setNull(5, Types.NULL);
			} else {
				psUpdateApontamento.setByte(5, apontamento.getCodTipoIdentificacaoCliente());
			}
			
			if (apontamento.getCodDocIdentificacaoCliente() == null) {
				psUpdateApontamento.setNull(6, Types.NULL);
			} else {
				psUpdateApontamento.setString(6, apontamento.getCodDocIdentificacaoCliente());
			}
		}
		
		psUpdateApontamento.addBatch();		
	}

	private void prepareNewInsertApontamento(
			PreparedStatement psInsertApontamento, Apontamento apontamento, boolean isSimulacao, ControleSimulacao loteSimulacao) throws SQLException {
		
		if (!isSimulacao) {
	        if (apontamento.getCodDocIdentificacaoCliente() == null) {
	        	psInsertApontamento.setNull(1, Types.NULL);
	        } else {
	        	psInsertApontamento.setString(1, apontamento.getCodDocIdentificacaoCliente());
	        }

	        if (apontamento.getCodigoLote() == null) {
	        	psInsertApontamento.setNull(2, Types.NULL);
	        } else {
	    		psInsertApontamento.setLong(2, apontamento.getCodigoLote());
	        }

	        if (apontamento.getCodigoStatusEventoAtual() == null) {
	        	psInsertApontamento.setNull(3, Types.NULL);
	        } else {
	    		psInsertApontamento.setByte(3, apontamento.getCodigoStatusEventoAtual());
	        }

	        if (apontamento.getCodTipoIdentificacaoCliente() == null) {
	        	psInsertApontamento.setNull(4, Types.NULL);
	        } else {
	    		psInsertApontamento.setByte(4, apontamento.getCodTipoIdentificacaoCliente());
	        }

	        if (apontamento.getDataApontamento() == null) {
	        	psInsertApontamento.setNull(5, Types.NULL);
	        } else {
	    		psInsertApontamento.setTimestamp(5, apontamento.getDataApontamento());
	        }

	        if (apontamento.getDataAtualizacaoCalculo() == null) {
	        	psInsertApontamento.setNull(6, Types.NULL);
	        } else {
	    		psInsertApontamento.setTimestamp(6, apontamento.getDataAtualizacaoCalculo());
	        }

	        if (apontamento.getFlagCarencia() == null) {
	        	psInsertApontamento.setNull(7, Types.NULL);
	        } else {
	    		psInsertApontamento.setByte(7, apontamento.getFlagCarencia());
	        }

	        if (apontamento.getFlagPontoCorte() == null) {
	        	psInsertApontamento.setNull(8, Types.NULL);
	        } else {
	    		psInsertApontamento.setByte(8, apontamento.getFlagPontoCorte());
	        }

	        if (apontamento.getFlagSuspeitoLD() == null) {
	        	psInsertApontamento.setNull(9, Types.NULL);
	        } else {
	    		psInsertApontamento.setByte(9, apontamento.getFlagSuspeitoLD());
	        }

	        if (apontamento.getValorApontamento() == null) {
	        	psInsertApontamento.setNull(10, Types.NULL);
	        } else {
	    		psInsertApontamento.setShort(10, apontamento.getValorApontamento());
	        }

	        if (apontamento.getValorPontoCorte() == null) {
	        	psInsertApontamento.setNull(11, Types.NULL);
	        } else {
	        	psInsertApontamento.setShort(11, apontamento.getValorPontoCorte());
	        }
		} else {
			
	        if (apontamento.getCodDocIdentificacaoCliente() == null) {
	        	psInsertApontamento.setNull(1, Types.NULL);
	        } else {
	        	psInsertApontamento.setString(1, apontamento.getCodDocIdentificacaoCliente());
	        }
			
			if (apontamento.getCodTipoIdentificacaoCliente() == null) {
				psInsertApontamento.setNull(2, Types.NULL);
			} else {
				psInsertApontamento.setByte(2, apontamento.getCodTipoIdentificacaoCliente());
			}

	        if (apontamento.getCodigoLote() == null) {
	        	psInsertApontamento.setNull(3, Types.NULL);
	        } else {
	    		psInsertApontamento.setLong(3, apontamento.getCodigoLote());
	        }

	        if (apontamento.getFlagSuspeitoLD() == null) {
	        	psInsertApontamento.setNull(4, Types.NULL);
	        } else {
	    		psInsertApontamento.setByte(4, apontamento.getFlagSuspeitoLD());
	        }

	        if (apontamento.getValorApontamento() == null) {
	        	psInsertApontamento.setNull(5, Types.NULL);
	        } else {
	    		psInsertApontamento.setShort(5, apontamento.getValorApontamento());
	        }

	        if (apontamento.getValorPontoCorte() == null) {
	        	psInsertApontamento.setNull(6, Types.NULL);
	        } else {
	        	psInsertApontamento.setShort(6, apontamento.getValorPontoCorte());
	        }
	        
        	psInsertApontamento.setLong(7, loteSimulacao.getCodIdentificadorSimulacao());
        	
        	psInsertApontamento.setTimestamp(8, apontamento.getDataApontamento());
		}
		            		
		psInsertApontamento.addBatch();		
	}

	private static String buildQueryInsertDetalhe(boolean isSimulacao) {
		if (isSimulacao) {
			return INSERT_DETALHE_APONTAMENTO_SIMULA;
		}
		
		return INSERT_DETALHE_APONTAMENTO;
	}

	private static String buildQueryUpdateApontamento(boolean isSimulacao) {
		if (isSimulacao) {
			return UPDATE_APONTAMENTOS_SIMULA;
		}
		
		return UPDATE_APONTAMENTOS;
	}

	private static String buildQueryInsertApontamento(boolean isSimulacao) {
		if (isSimulacao) {
			return INSERT_APONTAMENTOS_SIMULA;
		}
		
		return INSERT_APONTAMENTOS;
	}

	public Queue<String> buscarListaRegrasNaoAcumulativas(Apontamento apontamento, ControleSimulacao loteSimulacao, boolean isSimulacao) throws Exception {
    	Queue<String> lista = new LinkedList<String>();
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

          //TODO! ajuste considera o delete dos sublotes no fim do processamento com sucesso
            final PreparedStatement ps = con.prepareStatement(buildSelectRegrasAcum(loteSimulacao, isSimulacao));
            if ( !isSimulacao ) {
	            ps.setByte(1, apontamento.getCodTipoIdentificacaoCliente());
	            ps.setString(2, apontamento.getCodDocIdentificacaoCliente());
            } else {
	            ps.setByte(1, apontamento.getCodTipoIdentificacaoCliente());
	            ps.setString(2, apontamento.getCodDocIdentificacaoCliente());
            	ps.setLong(3, loteSimulacao.getCodIdentificadorSimulacao());
            }

            rs = ps.executeQuery();

            while (rs.next()) {
            	lista.add(apontamento.getCodTipoIdentificacaoCliente() + apontamento.getCodDocIdentificacaoCliente() + rs.getString("CD_REGRA"));
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	
    	return lista;
	}

	private static String buildSelectRegrasAcum(ControleSimulacao loteSimulacao, boolean isSimulacao) {
		if (!isSimulacao) {
			return SELECT_REGRAS_ACUMULATIVAS;
		}
		
		return SELECT_REGRAS_ACUMULATIVAS_SIMULA;
	}

	public Map<Timestamp, Queue<Integer>> buscarRegrasApontamentosAnteriores(long qtdDiaCarencia, List<String> array,
			byte codTipoIdentificacaoCliente, String codDocIdentificacaoCliente) throws Exception {
		Map<Timestamp, Queue<Integer>> mapa = new HashMap<Timestamp, Queue<Integer>>();
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();
            
            String query = SELECT_REGRAS_APONTAMENTOS_ANTERIORES;

            StringBuilder sb = new StringBuilder();
            for( int i = 0 ; i < array.size(); i++ ) {
            	sb.append(array.get(i) + ",");
            }
            if(sb.length() > 1)
            	sb.deleteCharAt(sb.length()-1);

            query = query.replaceAll(":statusFim", sb.toString());
            query = query.replaceAll(":qtdDiaCarencia", qtdDiaCarencia+"");

            final PreparedStatement ps = con.prepareStatement(query);
            ps.setByte(1, codTipoIdentificacaoCliente);
            ps.setString(2, codDocIdentificacaoCliente);
            ps.setByte(3, codTipoIdentificacaoCliente);
            ps.setString(4, codDocIdentificacaoCliente);

            rs = ps.executeQuery();

            while (rs.next()) {
            	
            	if(mapa.containsKey(rs.getTimestamp("DT_APONTAMENTO"))) {
                	final Queue<Integer> regras = mapa.get(rs.getTimestamp("DT_APONTAMENTO"));
                	// Não podemos adicionar a mesma regra mais de 1 vez por apontamento, pois veremos apenas se o cliente foi
                	// apontado anteriormente pelo conjunto de regra e não pela QUANTIDADE de disparos das regras...
                	if (!regras.contains(rs.getInt("CD_REGRA"))) {
						regras.add(rs.getInt("CD_REGRA"));
						mapa.put(rs.getTimestamp("DT_APONTAMENTO"), regras);
					}
                } else {
                    final Queue<Integer> regras = new ConcurrentLinkedQueue<Integer>();
                    regras.add(rs.getInt("CD_REGRA"));
                    mapa.put(rs.getTimestamp("DT_APONTAMENTO"), regras);
                }
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	
    	return mapa;
	}

	public Boolean existeRegraGravissima(Apontamento apontamento, ControleSimulacao loteSimulacao, boolean isSimulacao) throws Exception {
        Connection con = null;
        ResultSet rs = null;
        
        Boolean b = false;
    	
    	try {
            con = ConnectionPool.getConnection();

            final PreparedStatement ps = con.prepareStatement(buildQueryVerificaGravissima(isSimulacao));
            if (!isSimulacao) {
	            ps.setTimestamp(1, apontamento.getDataApontamento());
	            ps.setByte(2, apontamento.getCodTipoIdentificacaoCliente());
	            ps.setString(3, apontamento.getCodDocIdentificacaoCliente());
	            ps.setLong(4, apontamento.getCodigoLote());
            } else {
            	ps.setLong(1, loteSimulacao.getCodIdentificadorSimulacao());
            	ps.setByte(2, apontamento.getCodTipoIdentificacaoCliente());
            	ps.setString(3, apontamento.getCodDocIdentificacaoCliente());
            	ps.setLong(4, loteSimulacao.getCodIdentificadorSimulacao());
            }

            rs = ps.executeQuery();

            if (rs.next()) {
            	
            	b = true;
            	
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	
    	return b;
		
	}

	private static String buildQueryVerificaGravissima(boolean isSimulacao) {
		if (!isSimulacao) {
			return EXISTE_REGRA_GRAVISSIMA;
		}
		
		return EXISTE_REGRA_GRAVISSIMA_SIMULA;
	}

	public Queue<Integer> buscarRegrasApontamentoCorrente(
			Apontamento apontamento) throws Exception {
		Queue<Integer> regras = new LinkedList<Integer>();
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

            final PreparedStatement ps = con.prepareStatement(SELECT_REGRAS_APONTAMENTO_CORRENTE);
            ps.setTimestamp(1, apontamento.getDataApontamento());
            ps.setByte(2, apontamento.getCodTipoIdentificacaoCliente());
            ps.setString(3, apontamento.getCodDocIdentificacaoCliente());
            ps.setLong(4, apontamento.getCodigoLote());

            rs = ps.executeQuery();
            
            while (rs.next()) {            	
            	regras.add(rs.getInt("CD_REGRA"));
            }

            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	
    	return regras;
	}

	public void calculaPontuacao() throws Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection(); 
            con.setAutoCommit(false); 
            
            final Statement psCalculaPontuacao = con.createStatement();
            
            psCalculaPontuacao.execute(CALCULA_PONTUACAO);
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Houve um erro ao calcular pontuação...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao calcular pontuação...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();               
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }
		
	}

	public void calculaPontoCorte(Sublote sublote, ControleSimulacao loteSimulacao, boolean isSimulacao) throws Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection(); 
            con.setAutoCommit(false); 
            
            final Statement psCalculaPontuacao = con.createStatement();
            
            String query = "";
            
            if(isSimulacao) {
            	query = CALCULA_PONTO_CORTE_SIMULA.replace(":cd_lote", loteSimulacao.getCodLote().toString());
            	query = query.replace(":cd_simula", loteSimulacao.getCodIdentificadorSimulacao().toString());
            } else {
            	query = CALCULA_PONTO_CORTE.replace(":cd_lote", sublote.getCoLote().toString());
            }
            
            psCalculaPontuacao.execute(query);
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Houve um erro ao calcular pontuação...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao calcular pontuação...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();               
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }
		
	}

	public void transporteAgregados(Long coLote) throws Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            
            final Statement sTransporteAgregados = con.createStatement();
            
            String query = TRANSPORTE_AGREGADOS;
            
            query = query.replace(":cd_lote", coLote.toString());
            
    		for (int i = 0; i < query.trim().split(";").length; i++) {
    			sTransporteAgregados.addBatch(query.split(";")[i]);
    		}
            
            sTransporteAgregados.executeBatch();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Houve um erro ao transportar clientes agregados...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao transportar clientes agregados...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();               
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }
		
	}

	public void transporteAguardandoAnalise() throws Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            
            final Statement sTransporteAguardandoAnalise = con.createStatement();
            
            String query = TRANSPORTE_AGUARDANDO_ANALISE;
            
    		for (int i = 0; i < query.trim().split(";").length; i++) {
    			sTransporteAguardandoAnalise.addBatch(query.split(";")[i]);
    		}
            
            sTransporteAguardandoAnalise.executeBatch();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Houve um erro ao transportar clientes com status 0 para histórico...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao transportar clientes com status 0 para histórico...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();               
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }
		
	}
	
	public void transporteParecerAgencia() throws Exception {
		Connection con = null;
		try {
			con = ConnectionPool.getConnection();
			con.setAutoCommit(false);
			
			final Statement sTransporteAguardandoAnalise = con.createStatement();
			
			String query = TRANSPORTE_PARECER_AGENCIA;
			
			for (int i = 0; i < query.trim().split(";").length; i++) {
				sTransporteAguardandoAnalise.addBatch(query.split(";")[i]);
			}
			
			sTransporteAguardandoAnalise.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			log.error("Houve um erro ao transportar clientes com status 6 para histórico...");
			throw e;
		} catch (Exception e) {
			log.error("Houve um erro ao transportar clientes com status 6 para histórico...");
			throw e;
		} finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();               
				}
			} catch (SQLException e) {
				log.error("Não foi possível fechar a conexão...");
				throw e;
			}
		}
		
	}

	public void concluirAnalise(Queue<Apontamento> apontamentos, ControleSimulacao loteSimula, boolean isSimulacao) throws Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            
            // TODO - Update na TB_APONTAMENTO e Insert na TB_EVENTO
            final PreparedStatement psInsertEvento = con.prepareStatement(INSERT_EVENTO);
            //TODO! AJUSTAR ESTA QUERY (delete de sublotes no fim com sucesso)
            final PreparedStatement psUpdateApontamento = con.prepareStatement(buildUpdateApontamentosQuery(loteSimula));
            
            while (!apontamentos.isEmpty()) {
            	Apontamento apontamento = apontamentos.poll();
            	
            	// Colocando a data corrente como data para o parecer na tabela de apontamentos e a data de evento na tabela de eventos...
            	apontamento.setDataUltimoParecer(new Timestamp(System.currentTimeMillis()));

                prepareUpdateConclusaoAnalise(psUpdateApontamento, apontamento, isSimulacao, loteSimula);
        		
        		psUpdateApontamento.addBatch();

                if (apontamento.getFlagSuspeitoLD() == 1 && !isSimulacao) {
					if (apontamento.getCodTipoIdentificacaoCliente() == null) {
						psInsertEvento.setNull(1, Types.NULL);
					} else {
						psInsertEvento.setByte(1, apontamento
								.getCodTipoIdentificacaoCliente());
					}
					if (apontamento.getCodDocIdentificacaoCliente() == null) {
						psInsertEvento.setNull(2, Types.NULL);
					} else {
						psInsertEvento
								.setString(2, apontamento
										.getCodDocIdentificacaoCliente());
					}
					if (apontamento.getDataApontamento() == null) {
						psInsertEvento.setNull(3, Types.NULL);
					} else {
						psInsertEvento.setTimestamp(3,
								apontamento.getDataApontamento());
					}
					if (apontamento.getDataUltimoParecer() == null) {
						psInsertEvento.setNull(4, Types.NULL);
					} else {
						psInsertEvento.setTimestamp(4,
								apontamento.getDataUltimoParecer());
					}
					if (apontamento.getCodigoLote() == null) {
						psInsertEvento.setNull(5, Types.NULL);
					} else {
						psInsertEvento.setLong(5,
								apontamento.getCodigoLote());
					}
					psInsertEvento.addBatch();
				}
            }
            
            psUpdateApontamento.executeBatch();
            if (!isSimulacao) {
            	psInsertEvento.executeBatch();
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Houve um erro ao inserir os dados de Apontamentos...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao inserir os dados de Apontamentos...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();               
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }
		
	}

	private void prepareUpdateConclusaoAnalise(
			PreparedStatement psUpdateApontamento, Apontamento apontamento,
			boolean isSimulacao, ControleSimulacao loteSimula) throws SQLException {
		if (isSimulacao) {
	        if (apontamento.getFlagSuspeitoLD() == null) {
	        	psUpdateApontamento.setNull(1, Types.NULL);
	        } else {
	    		psUpdateApontamento.setByte(1, apontamento.getFlagSuspeitoLD());
	        }
	        
	        if (apontamento.getCodDocIdentificacaoCliente() == null) {
	        	psUpdateApontamento.setNull(2, Types.NULL);
	        } else {
	        	psUpdateApontamento.setString(2, apontamento.getCodDocIdentificacaoCliente());
	        }
	        
	        if (apontamento.getCodTipoIdentificacaoCliente() == null) {
	        	psUpdateApontamento.setNull(3, Types.NULL);
	        } else {
	    		psUpdateApontamento.setByte(3, apontamento.getCodTipoIdentificacaoCliente());
	        }
	        
	        psUpdateApontamento.setLong(4, loteSimula.getCodIdentificadorSimulacao());
	        
	        return;
		}
		
		if (apontamento.getCodigoStatusEventoAtual() == null) {
        	psUpdateApontamento.setNull(1, Types.NULL);
        } else {
    		psUpdateApontamento.setByte(1, apontamento.getCodigoStatusEventoAtual());
        }

        if (apontamento.getFlagCarencia() == null) {
        	psUpdateApontamento.setNull(2, Types.NULL);
        } else {
    		psUpdateApontamento.setByte(2, apontamento.getFlagCarencia());
        }

        if (apontamento.getFlagPontoCorte() == null) {
        	psUpdateApontamento.setNull(3, Types.NULL);
        } else {
    		psUpdateApontamento.setByte(3, apontamento.getFlagPontoCorte());
        }

        if (apontamento.getFlagSuspeitoLD() == null) {
        	psUpdateApontamento.setNull(4, Types.NULL);
        } else {
    		psUpdateApontamento.setByte(4, apontamento.getFlagSuspeitoLD());
        }

        if (apontamento.getDataUltimoParecer() == null) {
        	psUpdateApontamento.setNull(5, Types.NULL);
        } else {
    		psUpdateApontamento.setTimestamp(5, apontamento.getDataUltimoParecer());
        }

        if (apontamento.getDataApontamento() == null) {
        	psUpdateApontamento.setNull(6, Types.NULL);
        } else {
    		psUpdateApontamento.setTimestamp(6, apontamento.getDataApontamento());
        }

        if (apontamento.getCodTipoIdentificacaoCliente() == null) {
        	psUpdateApontamento.setNull(7, Types.NULL);
        } else {
    		psUpdateApontamento.setByte(7, apontamento.getCodTipoIdentificacaoCliente());
        }

        if (apontamento.getCodDocIdentificacaoCliente() == null) {
        	psUpdateApontamento.setNull(8, Types.NULL);
        } else {
    		psUpdateApontamento.setString(8, apontamento.getCodDocIdentificacaoCliente());
        }	

        if (apontamento.getCodigoLote() == null) {
        	psUpdateApontamento.setNull(9, Types.NULL);
        } else {
    		psUpdateApontamento.setLong(9, apontamento.getCodigoLote());
        }	
	}

	private static String buildUpdateApontamentosQuery(
			ControleSimulacao loteSimula) {
		if ( loteSimula == null ) {
			return UPDATE_APONTAMENTOS_CONCLUSAO_ANALISE;
		}
		
		return UPDATE_APONTAMENTOS_CONCLUSAO_ANALISE_SIMULA;
	}

	public Queue<Integer> getTotalPaginacao(
			Sublote sublote, ControleSimulacao loteSimulacao, boolean isSimulacao, long qtdRowsPerPage) throws Exception {
    	Queue<Integer> paginacao = new LinkedList<Integer>();
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

            final PreparedStatement ps = con.prepareStatement(isSimulacao ? SELECT_TOTAL_APONTAMENTOS_LOTE_SIMULA : SELECT_TOTAL_APONTAMENTOS_LOTE);
            if (!isSimulacao) {
            	ps.setLong(1, sublote.getCoLote());
            } else {
            	ps.setLong(1, loteSimulacao.getCodIdentificadorSimulacao()); 
            	ps.setLong(2, sublote.getCoLote());
            }

            rs = ps.executeQuery();

            if (rs.next()) {
            	long total = rs.getLong(1);
            	int count = 0;
            	for(long i = 0; i < total;i = i + qtdRowsPerPage) {
            		paginacao.add(count);
            		count++;            		
            	}
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	
    	return paginacao;
	}

	public Queue<Apontamento> buscarApontamentosLote(
			Sublote sublote, ControleSimulacao loteSimulacao, boolean isSimulacao, int page, long qtdRowsPerPage) throws Exception {
    	Queue<Apontamento> apontamentos = new LinkedList<Apontamento>();
    	Apontamento apontamento = null;
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

            final PreparedStatement ps = con.prepareStatement(buildQuerySelectApontamentosLote(isSimulacao));
            if (!isSimulacao) {
            	ps.setLong(1, sublote.getCoLote());
            	ps.setLong(2, page*qtdRowsPerPage);
            	ps.setLong(3, qtdRowsPerPage);
            } else {
            	ps.setLong(1, loteSimulacao.getCodIdentificadorSimulacao());
            	ps.setLong(2, sublote.getCoLote());
            }

            rs = ps.executeQuery();

            while (rs.next()) {
            	apontamento = buildApontamento(loteSimulacao, isSimulacao, rs);
            	apontamentos.add(apontamento);
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	
    	return apontamentos;
	}

	private static String buildQuerySelectApontamentosLote(boolean isSimulacao) {
		if (!isSimulacao) {
			return SELECT_APONTAMENTOS_LOTE_MENSAL;
		}
		return SELECT_APONTAMENTOS_LOTE_SIMULA_MENSAL;
	}

	public List<String> getEventosFimInvestiga() throws Exception {
		List<String> lista = new ArrayList<String>();
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

          //TODO! ajuste considera o delete dos sublotes no fim do processamento com sucesso
            final PreparedStatement ps = con.prepareStatement("SELECT CD_STTUS_EVENTO FROM TB_STTUS_EVENTO WHERE FL_FIM_INVTGA = 1 AND CD_STTUS_EVENTO NOT IN (1,30)");

            rs = ps.executeQuery();

            while (rs.next()) {
            	lista.add(rs.getString("CD_STTUS_EVENTO"));
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar parâmetros...");
            throw e1;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e2) {
                log.error("Não foi possível fechar a conexão.");
                throw e2;
            }
        }
    	return lista;
	}

	public void transporteApontamentosAtrasados(Long coLote) throws Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            
            final Statement sTransporteAguardandoAnalise = con.createStatement();
            
            String query = TRANSPORTE_APONTAMENTOS_ATRASADOS;
            
            query = query.replace(":cd_lote", coLote.toString());
            
    		for (int i = 0; i < query.trim().split(";").length; i++) {
    			sTransporteAguardandoAnalise.addBatch(query.split(";")[i]);
    		}
            
            sTransporteAguardandoAnalise.executeBatch();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Houve um erro ao transportar clientes com status 2 atrasados para histórico...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao transportar clientes com status 2 atrasados para histórico...");
            throw e;
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();               
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
                throw e;
            }
        }
		
	}

}