package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.SUBLOTE;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.Sublote;

public class SubLoteDAO implements Configuracao {

    private final static Logger log = LoggerFactory.getLogger(SubLoteDAO.class);

    public enum EstadoProcessamento {
        PROCESSANDO, SUCESSO, ERRO, REPROCESSO
    }
    
    static final String query = carregarQuery(SUBLOTE, "query1.sql");

    static final String queryLoteProcessado = carregarQuery(SUBLOTE, "query_lote_processado.sql");

    static final String queryMaquina = carregarQuery(SUBLOTE, "query_maquina.sql", props_conf.getProperty("nome_maquina"));
    
	static final String QUERY_SIMULACAO = carregarQuery(SUBLOTE, "query_busca_simulacao.sql");

	static final String QUERY_CRIACAO_SUBLOTES = carregarQuery(SUBLOTE, "query_criacao_sublotes_simula.sql");

    /**
     * Retorna sublote para processamento e marca o inicio da analise do mesmo
     * @param loteSimulacao
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static Sublote getNextOpenSublote(ControleSimulacao loteSimula, boolean isSimulacao) throws SQLException, Exception {
    	// Se for simulação e o lote de simulação for igual a null, 
    	// então retornamos sublote nulo para indicar falta de sublote ao processamento de simulação
    	if(isSimulacao && loteSimula == null) {
    		return null;
    	}

        Connection con = null;
        ResultSet rs = null;
        Sublote resul = null;

        boolean reprocesso = false;

        try {
            con = ConnectionPool.getConnection();

            rs = con.prepareStatement(buildQueryMaquina(isSimulacao, loteSimula)).executeQuery();
            if (rs.next()) {
                Sublote sublote = new Sublote(rs.getLong("cd_lote"), rs.getShort("cd_sublote"), rs.getLong("cd_lote") > 999999 ? (byte) 1 : (byte) 2);
                if(sublote.getTipoProcessamento() == 2 && sublote.getCoLote() < 200001) {
                	sublote = null;
                    log.error("Erro...");
                    log.error("O lote mensal não possui a estrutura \"YYYYMM\".");
                    log.error("Necessário construir um lote mensal que tenha a seguinte estrutura - \"YYYYMM\" - para refletir ano e mês do processo corrente.");
                    log.error("Para liberar o próximo lote é necessário apagar este lote mensal com a estrutura errada.  Caso contrário esse loop continuará com estas mesmas mensagens.");
                    log.error("Abrindo contagem de espera por 10 minutos para buscar próximo lote disponível...");
                    Thread.sleep(UM_MINUTO*10);
                    log.error("Buscando novo lote disponível após 10 minutos...\n\n");
                }
                if(sublote != null) atualizarEstado(sublote, EstadoProcessamento.REPROCESSO, loteSimula, isSimulacao);
                resul = sublote;
                reprocesso = true;
            }
            rs.close();

            rs = con.prepareStatement(buildDefaultQuery(isSimulacao, loteSimula)).executeQuery();
            if (!reprocesso && rs.next()) {
                executarLock(buildLockQuery(isSimulacao));
                Sublote sublote = new Sublote(rs.getLong("cd_lote"), rs.getShort("cd_sublote"), rs.getLong("cd_lote") > 999999 ? (byte) 1 : (byte) 2);
                if(sublote.getTipoProcessamento() == 2 && sublote.getCoLote() < 200001) {
                	sublote = null;
                    log.error("Erro...");
                    log.error("O lote mensal não possui a estrutura \"YYYYMM\".");
                    log.error("Necessário construir um lote mensal que tenha a seguinte estrutura - \"YYYYMM\" - para refletir ano e mês do processo corrente.");
                    log.error("Para liberar o próximo lote é necessário apagar este lote mensal com a estrutura errada.  Caso contrário esse loop continuará com estas mesmas mensagens.");
                    log.error("Abrindo contagem de espera por 10 minutos para buscar próximo lote disponível...");
                    Thread.sleep(UM_MINUTO*10);
                    log.error("Buscando novo lote disponível após 10 minutos...\n\n");
                }
                if(sublote != null) atualizarEstado(sublote, EstadoProcessamento.PROCESSANDO, loteSimula, isSimulacao);
                resul = sublote;
                executarLock(buildUnlockQuery(isSimulacao));
                rs.close();
                if(sublote != null) inicioLote(sublote.getCoLote(), loteSimula, isSimulacao);
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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
        return resul;
    }
    
    private static String buildLockQuery(boolean isSimulacaoAnalise) {
    	if (!isSimulacaoAnalise) {
    		return carregarQuery(SUBLOTE, "query_lock.sql");
    	}
    	return carregarQuery(SUBLOTE, "query_lock.sql").replaceAll( "tb_sub_lote" , "tb_sub_lote_simula");
    }

    private static String buildUnlockQuery(boolean isSimulacaoAnalise) {
		if (!isSimulacaoAnalise) {
			return carregarQuery(SUBLOTE, "query_unlock.sql");
		}
		return carregarQuery(SUBLOTE, "query_unlock.sql").replaceAll( "tb_sub_lote" , "tb_sub_lote_simula");
	}

	private static String buildDefaultQuery(boolean isSimulacaoAnalise, ControleSimulacao loteSimula) {
    	if (!isSimulacaoAnalise) {
			return query;
		}
    	
    	return query.replaceAll( "tb_sub_lote" , "tb_sub_lote_simula")
				.replace("dt_fim_carga_analise is not null", "dt_fim_carga_analise is not null and cd_identf_simula = " + loteSimula.getCodIdentificadorSimulacao());
	}

	private static String buildQueryMaquina(boolean isSimulacaoAnalise, ControleSimulacao loteSimula) {
    	if (!isSimulacaoAnalise) {
			return queryMaquina;
		}
    	
    	return queryMaquina.replaceAll( "tb_sub_lote" , "tb_sub_lote_simula")
				.replace("nm_maquina = ", " cd_identf_simula = " + loteSimula.getCodIdentificadorSimulacao() + "and nm_maquina = ");
	}

	public static ControleSimulacao getLoteSimulacao() throws SQLException, Exception {
    	ControleSimulacao controleSimula = null;
        Connection con = null;
        ResultSet rs = null;
        
        boolean createSublote = false;

        try {
            con = ConnectionPool.getConnection();

            rs = con.prepareStatement(buildQuerySimulacao()).executeQuery();
            if (rs.next()) {
            	controleSimula = new ControleSimulacao(
            			rs.getLong("cd_identf_simula"), 
            			rs.getLong("cd_lote"), 
            			rs.getDate("DT_FIM_ANLSE"), 
            			rs.getDate("DT_INICIO_ANLSE"), 
            			rs.getDate("DT_SIMULA"), 
            			rs.getLong("QT_CLIE_PROCES"), 
            			rs.getLong("QT_CLIE_SUSP"), 
            			rs.getInt("VL_PONTO_CORTE"));
            	createSublote = true;
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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
        // Se for um novo controle simulação, vamos criar os sublotes...
        if(createSublote) {
        	criarSubLotesSimulacao(controleSimula);
        }
        return controleSimula;
    }
    
    private static String buildQuerySimulacao() {
		return QUERY_SIMULACAO;
	}

	public static long getLoteRecuo(boolean isSimulacaoAnalise, ControleSimulacao loteSimulacao) throws SQLException, Exception {

        long lote = 0;
        Connection con = null;
        ResultSet rs = null;

        boolean reprocesso = false;

        try {
            con = ConnectionPool.getConnection();

            rs = con.prepareStatement(buildQueryMaquina(isSimulacaoAnalise, loteSimulacao)).executeQuery();
            if (rs.next()) {
                lote = rs.getLong("cd_lote");
                reprocesso = true;
            }
            rs.close();

            if (!reprocesso) {
                if ( isSimulacaoAnalise ) {
                	if ( loteSimulacao != null ) {
                		criarSubLotesSimulacao(loteSimulacao);
                	}
                }
            	
                rs = con.prepareStatement(buildDefaultQuery(isSimulacaoAnalise, loteSimulacao)).executeQuery();
                if (rs.next()) {
                    lote = rs.getLong("cd_lote");
                }
                rs.close();
            }

            if (lote == 0) {
                rs = con.prepareStatement(buildQueryProcessados(isSimulacaoAnalise, loteSimulacao)).executeQuery();
                if (rs.next()) {
                    lote = rs.getLong("cd_lote");
                }
                rs.close();
            }
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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
        return lote;
    }

    private static String buildQueryProcessados(boolean isSimulacaoAnalise, ControleSimulacao loteSimula) {
    	if (!isSimulacaoAnalise) {
    		return queryLoteProcessado;
		}
    	return queryLoteProcessado.replaceAll( "tb_sub_lote" , "tb_sub_lote_simula")
				.replace("cd_processamento = 'X'", "cd_processamento = 'X' and cd_identf_simula = " + loteSimula.getCodIdentificadorSimulacao());
	}

	private static void criarSubLotesSimulacao(ControleSimulacao loteSimulacao) throws SQLException, Exception {
        Connection con = null;

        try {
			con = ConnectionPool.getConnection();
			
			PreparedStatement deleteTMP = con.prepareStatement("DELETE FROM TB_SUB_LOTE_SIMULA WHERE CD_IDENTF_SIMULA = ?");
			deleteTMP.setLong(1, loteSimulacao.getCodIdentificadorSimulacao());
			deleteTMP.execute();
			
			PreparedStatement ps = con.prepareStatement(getQueryCriacaoSublotesSimula(loteSimulacao));
			ps.setLong(1, loteSimulacao.getCodIdentificadorSimulacao());
			ps.setLong(2, loteSimulacao.getCodLote());
			ps.execute();
		} catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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

	private static String getQueryCriacaoSublotesSimula(ControleSimulacao loteSimulacao) {
		return QUERY_CRIACAO_SUBLOTES;
	}

	public static void executarLock(String query) throws SQLException, Exception {
        Connection con = null;
        final PreparedStatement ps;
        try {
            con = ConnectionPool.getConnection();

            if (!isPostgreSQL(con.getMetaData().getDatabaseProductName())){
                return;
            }

            ps = con.prepareStatement(query);

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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

    private static boolean isPostgreSQL(final String databaseProductName) {
        if (databaseProductName == null){
            return false;
        }
        if (databaseProductName.equalsIgnoreCase("PostgreSQL")){
            return true;
        }
        return false;
    }

    public static void atualizarEstado(Sublote sublote, EstadoProcessamento estado, ControleSimulacao loteSimula, boolean isSimulacao) throws SQLException, Exception {
        Connection con = null;
        String statement;
        final PreparedStatement ps;
        try {
            con = ConnectionPool.getConnection();

            switch (estado) {
                case PROCESSANDO:
                	statement = carregarQuery(SUBLOTE, con.getMetaData(), "update_processando.sql", props_conf.getProperty("nome_maquina"));
                	if (isSimulacao) {
                		statement = statement.replaceAll( "tb_sub_lote" , "tb_sub_lote_simula")
                				.concat(" and cd_identf_simula = " + loteSimula.getCodIdentificadorSimulacao());
					}
                    ps = con.prepareStatement(statement);
                    ps.setLong(1, sublote.getCoLote());
                    ps.setShort(2, sublote.getCoSublote());
                    break;

                case REPROCESSO:
                    inicioLote(sublote.getCoLote(), loteSimula, isSimulacao);
                	statement = carregarQuery(SUBLOTE, con.getMetaData(), "update_reprocesso.sql", props_conf.getProperty("nome_maquina"));
                	if (isSimulacao) {
                		statement = statement.replaceAll( "tb_sub_lote" , "tb_sub_lote_simula")
                				.concat(" and cd_identf_simula = " + loteSimula.getCodIdentificadorSimulacao());
					}
                    ps = con.prepareStatement(statement);
                    ps.setLong(1, sublote.getCoLote());
                    ps.setShort(2, sublote.getCoSublote());
                    break;

                case ERRO:
                	statement = carregarQuery(SUBLOTE, "update_erro.sql");
                	if (isSimulacao) {
                		statement = statement.replaceAll( "tb_sub_lote" , "tb_sub_lote_simula")
                				.concat(" and cd_identf_simula = " + loteSimula.getCodIdentificadorSimulacao());
					}
                    ps = con.prepareStatement(statement);
                    ps.setLong(1, sublote.getCoLote());
                    ps.setShort(2, sublote.getCoSublote());
                    break;

                case SUCESSO:
                	statement = carregarQuery(SUBLOTE, "update_sucesso.sql");
                	if (isSimulacao) {
                		statement = statement.replaceAll( "tb_sub_lote" , "tb_sub_lote_simula")
                				.concat(" and cd_identf_simula = " + loteSimula.getCodIdentificadorSimulacao());
					}
                    ps = con.prepareStatement(statement);
                    ps.setLong(1, sublote.getCoLote());
                    ps.setShort(2, sublote.getCoSublote());
                    break;
                default:
                    statement = null;
                    ps = null;
                    break;
            }

            if (statement == null || ps == null) {
                return;
            }
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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

    public static void inicioLote(long lote, ControleSimulacao loteSimulacao, boolean isSimulacao) throws SQLException, Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            
            String query = buildInicioLoteQuery(isSimulacao);
            
            final PreparedStatement ps = con.prepareStatement(query);
            
            if (!isSimulacao) {
            	ps.setLong(1, lote);
            } else {
            	ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            	ps.setLong(2, loteSimulacao.getCodIdentificadorSimulacao());
            }
            
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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

    private static String buildInicioLoteQuery(boolean isSimulacao) {
    	if (isSimulacao) {
			return carregarQuery(SUBLOTE, "update_inicio_lote_simula.sql");
		}
    	
    	return carregarQuery(SUBLOTE, "update_inicio_lote.sql");
	}
    
    public static boolean checkfimLote(Long lote, ControleSimulacao loteSimulacao, boolean isSimulacao) throws SQLException, Exception {
        boolean loteCompleto = false;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            final String query = buildCheckFimLoteQuery(isSimulacao);
            final PreparedStatement ps = con.prepareStatement(query);
            if (!isSimulacao) {
	            ps.setLong(1, lote);
	            ps.setLong(2, lote);
	            ps.setLong(3, lote);
            } else {
            	ps.setLong(1, loteSimulacao.getCodIdentificadorSimulacao());
	            ps.setLong(2, lote);
	            ps.setLong(3, lote);
	            ps.setLong(4, lote);
            	ps.setLong(5, loteSimulacao.getCodIdentificadorSimulacao());
            }
            rs = ps.executeQuery();
            loteCompleto = rs.next();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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
        return loteCompleto;
    }
    
    private static String buildCheckFimLoteQuery(boolean isSimulacao) {
    	if (!isSimulacao) {
    		return carregarQuery(SUBLOTE, "check_fim_lote.sql");
		}
    	
    	return carregarQuery(SUBLOTE, "check_fim_lote_simula.sql");
	}

	public static boolean fimLote(Long lote, ControleSimulacao loteSimulacao, boolean isSimulacao) throws SQLException, Exception {
        boolean loteCompleto = false;
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            
          //TODO! AJUSTAR ESTA QUERY
            String query = buildFimLoteQuery(isSimulacao);
            final PreparedStatement ps = con.prepareStatement(query);
            if (!isSimulacao) {
	            ps.setLong(1, lote);
	            ps.setLong(2, lote);
	            ps.setLong(3, lote);
            } else {
            	ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            	ps.setLong(2, lote);
            	ps.setLong(3, lote);
            	ps.setLong(4, lote);
            	ps.setLong(5, loteSimulacao.getCodIdentificadorSimulacao());
            	ps.setLong(6, loteSimulacao.getCodIdentificadorSimulacao());
            }
            int update = ps.executeUpdate();
            loteCompleto = update != 0;
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar sublote disponível para processamento...");
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
        return loteCompleto;
    }

    private static String buildFimLoteQuery(boolean isSimulacao) {
    	if (!isSimulacao) {
			return carregarQuery(SUBLOTE, "update_fim_lote.sql");
		}
    	
    	return carregarQuery(SUBLOTE, "update_fim_lote_simula.sql");
	}

	public static void gravaLogProces(Long lote, ControleSimulacao loteSimulacao, boolean isSimulacao) throws SQLException, Exception {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            String query = carregarQuery(SUBLOTE, "insert_log_proces.sql");
            if(lote < 999999) {
            	query = query.replace("'DTEC_ANALISE' NM_PROCES,", "'DTEC_ANALISE_MENSAL' NM_PROCES,");
            }
            if(isSimulacao) {
            	query = query.replace("'DTEC_ANALISE' NM_PROCES,", "'DTEC_ANALISE_SIMULA' NM_PROCES,")
            			.replace("from tb_sub_lote where", "from tb_sub_lote_simula where cd_identf_simula = " + loteSimulacao.getCodIdentificadorSimulacao()+" and ");
            }
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, lote);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao atualizar Log Process...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao atualizar Log Process...");
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

	public static void gravaLogPerformance(Long lote) {
        Connection con = null;
        try {
            if (props_conf.getProperty("gravarLogPerformance") == "true") {
				con = ConnectionPool.getConnection();
				String query = carregarQuery(SUBLOTE, "insert_log_performance.sql");
				final PreparedStatement ps = con.prepareStatement(query);
				ps.setLong(1, lote);
				ps.executeUpdate();
				ps.close();
			}
        } catch (SQLException e) {
            log.error("Houve um erro ao inserir na Log Performance...", e);
        } catch (Exception e) {
            log.error("Houve um erro ao inserir na Log Performance...", e);
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...");
            }
        }
	}

	public static void executaREORG(Long numLote) {
        Connection con = null;
        List<String> lotes = new ArrayList<String>();
        String mes;
        
        try {
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			gc.setTime(sdf.parse(numLote + ""));
			mes = sdf.format(gc.getTime());
			lotes.add( mes );
			log.info("LOTE " + sdf.format(gc.getTime()) + " adicionado à lista");

    		for (int i = 0; i < 5; i++) {
				gc.setTime(sdf.parse(mes + ""));
				gc.add(GregorianCalendar.MONTH, -1);
				mes = sdf.format(gc.getTime());				
				lotes.add( mes );
			}
            con = ConnectionPool.getConnection(); 
            con.setAutoCommit(false); 

            executarReorgParticoes(lotes, con);

			runStatsOnGatheredTables(con);
            
            con.setAutoCommit(true);
        } catch (Throwable e) {
        	log.error("Houve um erro ao executar o REORG...", e);
        } finally {
            try {
                if (con != null && !con.isClosed()) {
    	            con.close();
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão...", e);
            }
        }
	}

    private static void executarReorgParticoes(List<String> lotes, Connection con) throws Throwable {
        for (String lote : lotes) {
        	Statement statementPartition = null;
        	CallableStatement statementStatistics = null;
            try {
				statementPartition = movePartition(con, lote);
				statementStatistics = runStatistcs(con, lote);
				runRebuildIndexes(con, lote);

            } catch(Throwable e) {
            	throw e;
            } finally {
                if(statementStatistics != null && !statementStatistics.isClosed())
    	            statementStatistics.close();
                if(statementPartition != null && !statementPartition.isClosed())
                	statementPartition.close();
			}
        }
	}
    
    private static void runRebuildIndexes(Connection con, String lote) throws SQLException {
    	String indexesToRebuild = Configuracao.props_conf.getProperty("indexes-to-rebuild");
    	String tablespaceIndexes = Configuracao.props_conf.getProperty("tablespace-indices");
    	
    	if(StringUtils.isBlank(indexesToRebuild))
    		return;
    	
    	for (String index : indexesToRebuild.split(",")) {
    		rebuildIndex(con, lote, tablespaceIndexes, index);
		}
    	
    }

	private static void rebuildIndex(Connection con, String lote, String tablespaceIndexes, String index)
			throws SQLException {
		Statement statement = null;
		try {
			
			statement = con.createStatement();
			
			String query = "ALTER INDEX :index     REBUILD PARTITION P:cd_lote01 TABLESPACE :tablespaceIndexes";
			
			query = query.replaceAll(":index", index);
			query = query.replaceAll(":cd_lote", lote);
			query = query.replaceAll(":tablespaceIndexes", tablespaceIndexes);
			
			log.info("Query \"ALTER INDEX\" REBUILD para a partição do mês "+ lote + ": [" + query + "]");

			statement.execute(query);

			con.commit();
			
		} catch(Throwable e) {
			log.error("Houve um erro ao executar o rebuild do índice "+index, e);
		} finally {
		    if(statement != null && !statement.isClosed())
		    	statement.close();
		}
	}

	private static void runStatsOnGatheredTables(Connection con) throws SQLException {
		
    	String tablesToGatherStats = Configuracao.props_conf.getProperty("tables-to-gather-stats");
    	
    	if(StringUtils.isBlank(tablesToGatherStats))
    		return;
    	
    	for (String table : tablesToGatherStats.split(",")) {
    		
    		CallableStatement statement = null;
    		try {
				String query = "EXECUTE PZO.PZOSPASP.PZOSPSSS('BLLS30','" + table + "');";
				log.info("Query de stats para a tabela "+ table + ": [" + query + "]");
				
				statement = con.prepareCall(query);
				
				statement.execute();
				
				con.commit();
    		} catch(Throwable e) {
    			log.error("Houve um erro ao executar stats on gathered tables para a tabela " + table, e);
    		} finally {
    		    if(statement != null && !statement.isClosed())
    		    	statement.close();
    		}
		}
	}

	private static CallableStatement runStatistcs(Connection con, String lote) throws SQLException {
		CallableStatement statement;
		// pegar e colocar em um array os 6 lotes anteriores ao lote corrente
		// Iterar sobre esse array para executar o reorg em cada lote deste array
		String query = carregarQuery(SUBLOTE, "executa_reorg.sql");
		query = query.replace(":cd_lote", lote);
		log.info("Query de reorg para a partição do mês "+ lote + ": [" + query + "]");

		statement = con.prepareCall(query);
		
		statement.execute();
		
		con.commit();
		return statement;
	}

	private static Statement movePartition(Connection con, String lote) throws SQLException {
		Statement statementAlter = con.createStatement();
		
		String queryAlter = "ALTER TABLE BLLS30.TB_TRANS MOVE PARTITION P"+ lote + "01 TABLESPACE BLL_030_DPRC UPDATE INDEXES";
		log.info("Query \"ALTER MOVE PARTITION\" para a partição do mês "+ lote + ": [" + queryAlter + "]");

		statementAlter.execute(queryAlter);

		con.commit();
		
		return statementAlter;
	}

    public static List<Long> getLotesRecuo(Long loteInicio, long loteFim) throws SQLException, Exception {

        Connection con = null;
        ResultSet rs = null;
        List<Long> lista = new ArrayList<Long>();

        final String consulta = carregarQuery(SUBLOTE, "query_lotes_recuo.sql");

        try {
            con = ConnectionPool.getConnection();
            final PreparedStatement ps = con.prepareStatement(consulta);
            ps.setLong(1, loteInicio);
            ps.setLong(2, loteFim);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getLong("cd_lote"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar lotes para o recuo parcial...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar lotes para o recuo parcial...");
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
        return lista;
    }
    
	public static Sublote checkLoteIncompleto(boolean isSimulacaoAnalise) throws Exception {
        Sublote resul = null;

        Connection con = null;
        ResultSet rs = null;

        final String consulta = carregarQuery(SUBLOTE, isSimulacaoAnalise ? "check_lote_incompleto_simula.sql" : "check_lote_incompleto.sql");

        try {
            con = ConnectionPool.getConnection();
            final PreparedStatement ps = con.prepareStatement(consulta);
            rs = ps.executeQuery();
            if (rs.next()) {
                Sublote sublote = new Sublote(rs.getLong("cd_lote"), (short) 1, rs.getLong("cd_lote") > 999999 ? (byte) 1 : (byte) 2);
                resul = sublote;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar lotes para o recuo parcial...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar lotes para o recuo parcial...");
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
        return resul;
	}

	public static Integer getLoteMensal() throws Exception {
        Connection con = null;
        ResultSet rs = null;
        
        int cdLote = 0;

        final String consulta = carregarQuery(SUBLOTE, "buscar_lote_mensal.sql");

        try {
            con = ConnectionPool.getConnection();
            final PreparedStatement ps = con.prepareStatement(consulta);
            rs = ps.executeQuery();
            if (rs.next()) {
				cdLote = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar lote mensal...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar lote mensal...");
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
        return cdLote;
	}

	public static Boolean confereLoteRisco(Integer cdLote) throws Exception {
        Connection con = null;
        ResultSet rs = null;

        String consulta = carregarQuery(SUBLOTE, "verificar_lote_risco.sql");
        
        consulta = consulta.replace(":cd_lote", cdLote+"");

        try {
            con = ConnectionPool.getConnection();
            final PreparedStatement ps = con.prepareStatement(consulta);
            rs = ps.executeQuery();
            if (rs.next()) {
				return true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar lote mensal...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar lote mensal...");
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
        return false;
	}

	public static void criarLoteMensal(Integer loteMensal) throws Exception {
        Connection con = null;

        final String insert = carregarQuery(SUBLOTE, "criar_lote_mensal.sql");

        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            final PreparedStatement ps = con.prepareStatement(insert);
            ps.setLong(1, loteMensal);
            ps.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao inserir lote mensal...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao inserir lote mensal...");
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

	public static void criarSubloteMensal(Integer loteMensal) throws Exception {
        Connection con = null;

        final String insert = carregarQuery(SUBLOTE, "criar_sublote_mensal.sql");

        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            final PreparedStatement ps = con.prepareStatement(insert);
            ps.setLong(1, loteMensal);
            ps.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            ps.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao inserir sublote mensal...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao inserir sublote mensal...");
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
