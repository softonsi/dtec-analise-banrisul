package br.com.softon.dtec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.ObjetoAnalise;
import br.com.softon.dtec.entity.ParametrosRegras;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.entity.Sublote;

public class ObjetoAnaliseDAO implements Configuracao {

    private final static Logger log = LoggerFactory.getLogger(ObjetoAnaliseDAO.class);

	public static List<ObjetoAnalise> getObjetosPorRegra(Regra regra, Sublote sublote, ControleSimulacao loteSimula, boolean isSimulacaoAnalise) {

        String query = regra.getTextoRegraDinamica();
        final List<ObjetoAnalise> lista = new ArrayList<ObjetoAnalise>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        boolean exit = false;
        int exitCode = 0;

        try {        
        	final Collection<ParametrosRegras> listaParametrosRegras = getParams(regra, loteSimula, isSimulacaoAnalise);
	
	        query = query.replaceAll(":cd_lote", sublote.getCoLote()+"");
	        query = query.replaceAll(":cd_sublote", sublote.getCoSublote()+"");
	        
	        if (!listaParametrosRegras.isEmpty()) {
				for (ParametrosRegras param : listaParametrosRegras) {
					query = query.replaceAll(":" + param.getNomeCampo(), param.getValorParametro()+"");
					query = query.replaceAll(":DS-" + param.getNomeCampo(), param.getDescricaoCampo());
					query = query.replaceAll(":LS-" + param.getNomeCampo(), param.getDescricaoParametro());
					query = query.replaceAll(":NM-" + param.getNomeCampo(), param.getNomeCampo()+"");
				} 
			}
	        
			con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            
            st = con.prepareStatement(query);
            st.setFetchSize(10000);
            // Buscar o timeout de execução das regras no arquivo de propriedades.  Default de 10 minutos caso não exista a propriedade.
            int timeout_sql_rules_seconds = props_conf.getProperty("timeout_sql_rules_seconds") == null ? 600 : 
				Integer.parseInt(props_conf.getProperty("timeout_sql_rules_seconds"));
            st.setQueryTimeout(timeout_sql_rules_seconds);
            log.debug("Início da busca das transações do sublote " + sublote.getCoSublote() + " do lote " + sublote.getCoLote() + " para a regra " + regra.getCodigoRegra());
            rs = st.executeQuery();
            while (rs.next()) {
            	ObjetoAnalise bean = ObjetoAnalise.build(rs, regra.getCodigoRegra(), sublote);
            	lista.add(bean);
            }
            log.debug("Fim da busca das transações do sublote " + sublote.getCoSublote() + " do lote " + sublote.getCoLote() + " para a regra " + regra.getCodigoRegra());
            rs.close();
            st.close();
        } catch (SQLException e) {            
            try {
				RegrasDAO.gravarErroRegra(regra,
						"A regra '" + regra.getCodigoRegra() + "' sofreu erro ao executar a query.\nErro:" + e);
			} catch (final Exception e1) {
				log.error("Não foi possível gerar ocorrência de erro para a regra '" + regra.getCodigoRegra() + "'.\nMotivo:" + e1);
			}
            log.error("Problemas com requisições no banco de dados ao executar a regra " + regra.getCodigoRegra() + "...");
            while (e != null) {
                log.error("SQLState.........:" + e.getSQLState());
                log.error("Código de Erro...:" + e.getErrorCode());
                log.error("Descrição do Erro:" + e.getMessage());
                e.printStackTrace(System.out);
                e = e.getNextException();
            }
        } catch (Exception e) {
            log.error("Erro desconhecido ao executar a regra " + regra.getCodigoRegra() + ".", e);
            
            try {
				RegrasDAO.gravarErroRegra(regra,
						"A regra '" + regra.getCodigoRegra() + "' sofreu erro ao executar a query.\nErro:" + e);
			} catch (final Exception e1) {
				log.error("Não foi possível gerar ocorrência de erro para a regra '" + regra.getCodigoRegra() + "'.\nMotivo:" + e1);
			}
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão", e);
            }
            if(exit) {
                System.exit(exitCode);
            }
        }
        return lista;
    }

	/**
	 * @param regra 
	 * @return
	 * @throws Exception 
	 */
	public static Collection<ParametrosRegras> getParams(Regra regra, ControleSimulacao loteSimula, boolean isSimulacaoAnalise) throws Exception {

        Connection con = null;
        ResultSet rs = null;

        final Collection<ParametrosRegras> listaParametrosRegras = new ArrayList<ParametrosRegras>();

        String consulta = "";
        
        if(isSimulacaoAnalise) {
        	consulta = "select * from tb_regra_parametro_simula where cd_regra = ? and cd_versao_sistema = ? and cd_identf_simula = ?";
        } else {
        	consulta = "select * from tb_regra_parametro where cd_regra = ? and cd_versao_sistema = ?";
        }

        try {
            con = ConnectionPool.getConnection();
            final PreparedStatement ps = con.prepareStatement(consulta);
            ps.setLong(1, regra.getCodigoRegra());
            ps.setLong(2, regra.getCodigoVersaoSistema());
            if(isSimulacaoAnalise) {
            	ps.setLong(3, loteSimula.getCodIdentificadorSimulacao());
            }
            rs = ps.executeQuery();
            while (rs.next()) {
            	
            	ParametrosRegras parametrosRegras = new ParametrosRegras(
            			rs.getString("CD_REGRA") != null ? rs.getInt("CD_REGRA") : null,
                    	rs.getString("CD_SEQ_REGRA") != null ? rs.getInt("CD_SEQ_REGRA") : null, 
                        rs.getString("CD_VERSAO_SISTEMA") != null ? rs.getByte("CD_VERSAO_SISTEMA") : null,
                        rs.getString("DS_CAMPO_PARAM") != null ? rs.getString("DS_CAMPO_PARAM") : null, 
                        rs.getString("DS_PARAM") != null ? rs.getString("DS_PARAM") : null, 
                        rs.getString("NM_CAMPO_PARAM") != null ? rs.getString("NM_CAMPO_PARAM") : null, 
                        rs.getString("VL_LIM_FIM") != null ? rs.getDouble("VL_LIM_FIM") : null,
                        rs.getString("VL_LIM_INIC") != null ? rs.getDouble("VL_LIM_INIC") : null,
                        rs.getString("CD_TP_ATRIB") != null ? rs.getByte("CD_TP_ATRIB") : null,
                        rs.getString("VL_PARAM") != null ? rs.getDouble("VL_PARAM") : null);
                
            	listaParametrosRegras.add(parametrosRegras);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {            
            try {
				RegrasDAO.gravarErroRegra(regra,
						"A regra '" + regra.getCodigoRegra() + "' sofreu erro ao executar a query de busca de parâmetros.\nErro:" + e);
			} catch (final Exception e1) {
				log.error("Não foi possível gerar ocorrência de erro para a regra '" + regra.getCodigoRegra() + "'.\nMotivo:" + e1);
			}
            log.error("Problemas com requisições no banco de dados ao executar a regra " + regra.getCodigoRegra() + "...");
            while (e != null) {
                log.error("SQLState.........:" + e.getSQLState());
                log.error("Código de Erro...:" + e.getErrorCode());
                log.error("Descrição do Erro:" + e.getMessage());
                e.printStackTrace(System.out);
                e = e.getNextException();
            }
        } catch (Exception e) {
            log.error("Erro desconhecido ao executar a regra " + regra.getCodigoRegra() + ".", e);
            
            try {
				RegrasDAO.gravarErroRegra(regra,
						"A regra '" + regra.getCodigoRegra() + "' sofreu erro ao executar a query.\nErro:" + e);
			} catch (final Exception e1) {
				log.error("Não foi possível gerar ocorrência de erro para a regra '" + regra.getCodigoRegra() + "'.\nMotivo:" + e1);
			}
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Não foi possível fechar a conexão", e);
            }
        }
		return listaParametrosRegras;
	}
}
