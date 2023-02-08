package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.REGRAS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.service.RegraHandle;

public class RegrasDAO {

    private final static Logger log = LoggerFactory.getLogger(RegrasDAO.class);

    private static final String QUERY = carregarQuery(REGRAS, "query.sql");
    private static final String QUERY_SIMULACAO = carregarQuery(REGRAS, "query_simulacao.sql");
    
    private static final String UPDATE_ERRO_REGRA = carregarQuery(REGRAS, "update_erro_regra.sql");
    private static final String QUERY_PARAMETRO_GLOBAL = carregarQuery(REGRAS, "query_parametro_global.sql");

	
    
    public static Map<String,Boolean> carregarParametrosGlobais() throws SQLException, Exception {
    	Map<String,Boolean> mapa = new HashMap<String, Boolean>();
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

            rs = con.prepareStatement(QUERY_PARAMETRO_GLOBAL).executeQuery();

            while (rs.next()) {
                String noParametro = rs.getString("nm_campo_param");
                Boolean icParametro = rs.getBoolean("fl_param");
                mapa.put(noParametro, icParametro);
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
    
    public static Map<String,Object> carregarParametrosGlobaisValor() throws SQLException, Exception {
    	Map<String,Object> mapa = new HashMap<String, Object>();
        Connection con = null;
        ResultSet rs = null;
    	
    	try {
            con = ConnectionPool.getConnection();

            rs = con.prepareStatement(QUERY_PARAMETRO_GLOBAL).executeQuery();

            while (rs.next()) {
                String noParametro = rs.getString("nm_campo_param");
                Long icParametro = rs.getLong("vl_param_real");
                mapa.put(noParametro, icParametro);
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

    public static RegraHandle carregarRegras(boolean isSimulacaoAnalise, ControleSimulacao loteSimulacao) throws SQLException, Exception {
        Connection con = null;
        ResultSet rs = null;

        final List<Regra> regras = new ArrayList<Regra>();

        final List<Regra> regrasRedeNeural = new ArrayList<Regra>();

        final List<Regra> regrasProcessamentoDiario = new ArrayList<Regra>();

        final List<Regra> regrasProcessamentoMensal = new ArrayList<Regra>();

        final List<Regra> regrasPosProcessamentoMensal = new ArrayList<Regra>();

        try {
            con = ConnectionPool.getConnection();

            PreparedStatement ps = null;

            if ( isSimulacaoAnalise ) {
            	ps = con.prepareStatement(QUERY_SIMULACAO);
            	ps.setLong(1, loteSimulacao.getCodIdentificadorSimulacao());
            	ps.setLong(2, loteSimulacao.getCodIdentificadorSimulacao());
            } else {
            	ps = con.prepareStatement(QUERY);
            }
            
            rs = ps.executeQuery();

            while (rs.next()) {
                
                Short codigoClasseRegra = rs.getShort("CD_CLASSE_REGRA");
        		String codigoSiscoaf = rs.getString("CD_SISCOAF");
    			Short codigoRegra = rs.getShort("CD_REGRA");
    			Byte codigoTipoRegra = rs.getByte("CD_TP_REGRA"); 
    			String codigoUsuario = rs.getString("CD_USU");
    			Byte codigoVersaoSistema = rs.getByte("CD_VERSAO_SISTEMA");
    			String descricaoErroRegra = rs.getString("DS_ERRO_REGRA");
    			String descricaoRegra = rs.getString("DS_REGRA"); 
    			Timestamp dataRegra = rs.getTimestamp("DT_REGRA");
    			Timestamp dataRegraDinamica = rs.getTimestamp("DT_REGRA_DINAMICA"); 
    			Byte flagAcumulaRegra = rs.getByte("FL_ACUM_REGRA");
    			Byte flagRegraAtiva = rs.getByte("FL_REGRA_ATIVA"); 
    			String nomeRegra = rs.getString("NM_REGRA");
    			String textoRegraDinamica = rs.getString("TX_REGRA_DINAMICA");
    			Byte valorPontuacao = rs.getByte("VL_PONTO");
    			Byte codigoTipoProcesso = rs.getByte("CD_TP_PROCES"); 
                
                Regra regra = new Regra(
                		codigoClasseRegra, 
                		codigoSiscoaf,
            			codigoRegra, 
            			codigoTipoRegra, 
            			codigoTipoProcesso,
            			codigoUsuario,
            			codigoVersaoSistema, 
            			descricaoErroRegra,
            			descricaoRegra, 
            			dataRegra,
            			dataRegraDinamica, 
            			flagAcumulaRegra,
            			flagRegraAtiva, 
            			nomeRegra,
            			textoRegraDinamica, 
            			valorPontuacao);
                
                regras.add(regra);
                if(codigoTipoRegra == 3) {
                	regrasRedeNeural.add(regra);
                }
                if(codigoTipoProcesso == 1) { // Processamento Diário...
                	regrasProcessamentoDiario.add(regra);
                }
                if(codigoTipoProcesso == 2) { // Processamento Mensal...
                	regrasProcessamentoMensal.add(regra);
                }
                if(codigoClasseRegra == 90) { // Regras de penalização e carência...
                	regrasPosProcessamentoMensal.add(regra);
                }
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar informações sobre regras...");
            throw e;
        } catch (Exception e1) {
            log.error("Houve um erro ao buscar informações sobre regras...");
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

        return new RegraHandle(regras, regrasRedeNeural, regrasProcessamentoDiario, regrasProcessamentoMensal, regrasPosProcessamentoMensal);
    }

    public static void gravarErroRegra(Regra regra, String erro) throws SQLException, Exception {
        Connection con = null;

        try {
            con = ConnectionPool.getConnection();
            final PreparedStatement psErroRegra = con.prepareStatement(UPDATE_ERRO_REGRA);

            psErroRegra.setString(1, erro);
            psErroRegra.setInt(2, regra.getCodigoRegra());
            psErroRegra.setByte(3, regra.getCodigoVersaoSistema());

            psErroRegra.execute();

        } catch (SQLException e) {
            log.error("Houve um erro ao buscar informações sobre regras...");
            log.error(e.getMessage());
            throw e;
            
        } catch (Exception e) {
            log.error("Houve um erro ao buscar informações sobre regras...");
            log.error(e.getMessage());
            throw e;

        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão.", e);
                throw e;
            }
        }
    }
}
