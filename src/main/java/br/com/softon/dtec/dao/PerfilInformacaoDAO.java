package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.PERFIL_INFORMACAO;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.VARIAVEL_PERFIL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoPerfisInformacoes;
import br.com.softon.dtec.entity.PerfilInformacao;
import br.com.softon.dtec.entity.Sublote;
import br.com.softon.dtec.entity.VariavelPerfil;

public class PerfilInformacaoDAO {

    private final static Logger log = LoggerFactory.getLogger(PerfilInformacaoDAO.class);

    private static final String QUERY = carregarQuery(PERFIL_INFORMACAO, "query.sql");

    private static final String QUERY_VARIAVEL_PERFIL = carregarQuery(VARIAVEL_PERFIL, "query.sql");

    public static ConjuntoPerfisInformacoes getConjuntoPerfisInformacoes(Sublote sublote) throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Set<PerfilInformacao> result = new HashSet<PerfilInformacao>();
        final PreparedStatement ps;
        
        // TODO - Implementar loop na tabela variavel perfil para buscar os perfis ativos e adicioná-los na busca do perfil
        Collection<VariavelPerfil> lista =  buscarVariaveisPerfilInformacao();
        
        // Se não houver variáveis para buscar o perfil, vamos retornar nulo...
        if(lista == null)
        	return null;
        
        StringBuilder sb = new StringBuilder();
        
        for(VariavelPerfil vp : lista) {
        	
        	byte var = 0;
        	// Variável que verificará os parametros a serem usados na montagem da query
        	// Se estiver nulo passa para o próximo loop...
        	if (vp.getDsPerfil() != null) {
        		// Se contiver parênteses indica que existem parametros...
				if (vp.getDsPerfil().contains("(") && vp.getDsPerfil().contains(")")) {
					var = 1;
				} else {
					// Se não houver parênteses indica que existe lixo no campo DS_PERFIL e deverá ser ignorado
					continue;
				}
				// entramos em outro loop para verificar se existe mais que 1 parametro...
				for (int i = 0; i < vp.getDsPerfil().length(); i++) {
					if (vp.getDsPerfil().charAt(i) == ',') {
						var++;
					}
				} 
			} else {
				continue;
			}
        	
        	String filtro =  " CD_IDENTF_PERFIL = "+vp.getCodIdentificacaoPerfil()+" AND ";
        	
            sb.append(QUERY +
            	"\n\tWHERE "+filtro+" EXISTS (\n\t\tSELECT 1 FROM TB_TRANS_ANLSE \n\t\t\tWHERE CD_LOTE = " + sublote.getCoLote() + " AND CD_SUBLOTE = " + sublote.getCoSublote());
            
        	if(vp.getNmVariavelPrimeira() != null && var >= 1) {
        		sb.append(" \n\t\t\tAND P.CD_VARIAVEL_PRIMEIRA = " + vp.getNmVariavelPrimeira());
        	}
        	if(vp.getNmVariavelSegunda() != null && var >= 2) {
        		sb.append(" \n\t\t\tAND P.CD_VARIAVEL_SEGUNDA = " + vp.getNmVariavelSegunda());
        	}
        	if(vp.getNmVariavelTerceira() != null && var >= 3) {
        		sb.append(" \n\t\t\tAND P.CD_VARIAVEL_TERCEIRA = " + vp.getNmVariavelTerceira());
        	}
        	if(vp.getNmVariavelQuarta() != null && var >= 4) {
        		sb.append(" \n\t\t\tAND P.CD_VARIAVEL_QUARTA = " + vp.getNmVariavelQuarta());
        	}        	
        	
            sb.append(")\n UNION ");
        }
        
        String query = "";
        
        if(sb.toString().contains(" UNION ")) {
        	query = sb.toString().substring(0, sb.toString().lastIndexOf(" UNION "));
        } else {
            // Se a query estiver em branco, vamos retornar nulo...
        	return null;
        }

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final PerfilInformacao perfil = new PerfilInformacao(
        				rs.getString("CD_IDENTF_PERFIL") != null ? rs.getShort("CD_IDENTF_PERFIL") : null,
        				rs.getString("CD_VARIAVEL_PRIMEIRA") != null ? rs.getString("CD_VARIAVEL_PRIMEIRA") : null,
        				rs.getString("CD_VARIAVEL_QUARTA") != null ? rs.getString("CD_VARIAVEL_QUARTA") : null,
        				rs.getString("CD_VARIAVEL_SEGUNDA") != null ? rs.getString("CD_VARIAVEL_SEGUNDA") : null,
        				rs.getString("CD_VARIAVEL_TERCEIRA") != null ? rs.getString("CD_VARIAVEL_TERCEIRA") : null,
        				rs.getString("DT_PERFIL") != null ? rs.getTimestamp("DT_PERFIL") : null,
        				rs.getString("QT_TOTAL") != null ? rs.getInt("QT_TOTAL") : null,
        				rs.getString("VL_DESVIO_DIARIO") != null ? rs.getDouble("VL_DESVIO_DIARIO") : null,
        				rs.getString("VL_MEDIO_DIARIO") != null ? rs.getDouble("VL_MEDIO_DIARIO") : null,
        				rs.getString("VL_TOTAL") != null ? rs.getDouble("VL_TOTAL") : null
                	);
                result.add(perfil);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Perfis de Informacao carregados.");
            return new ConjuntoPerfisInformacoes(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Perfis de Informações...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Perfis de Informações...");
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

	private static Collection<VariavelPerfil> buscarVariaveisPerfilInformacao() throws Exception {
        Connection con = null;
        ResultSet rs = null;
        Collection<VariavelPerfil> result = new LinkedList<VariavelPerfil>();
        final PreparedStatement ps;

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            ps = con.prepareStatement(QUERY_VARIAVEL_PERFIL);
            ps.setByte(1, (byte) 1); // 1 - Tipo Perfil Informação
            rs = ps.executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final VariavelPerfil perfil = new VariavelPerfil(
        				rs.getString("CD_IDENTF_PERFIL") != null ? rs.getShort("CD_IDENTF_PERFIL") : null,
                		rs.getString("DS_PERFIL") != null ? rs.getString("DS_PERFIL") : null,
        				rs.getString("NM_VARIAVEL_PRIMEIRA") != null ? rs.getString("NM_VARIAVEL_PRIMEIRA") : null,
        				rs.getString("NM_VARIAVEL_QUARTA") != null ? rs.getString("NM_VARIAVEL_QUARTA") : null,
        				rs.getString("NM_VARIAVEL_SEGUNDA") != null ? rs.getString("NM_VARIAVEL_SEGUNDA") : null,
                		rs.getString("NM_VARIAVEL_TERCEIRA") != null ? rs.getString("NM_VARIAVEL_TERCEIRA") : null,
                        rs.getString("NM_FILTRO_ADICIONAL") != null ? rs.getString("NM_FILTRO_ADICIONAL") : null,
        				rs.getString("QT_DIA_RECUO") != null ? rs.getShort("QT_DIA_RECUO") : null,
        				rs.getString("CD_TP_PERFIL") != null ? rs.getByte("CD_TP_PERFIL") : null,
        				rs.getString("FL_ATIVO") != null ? rs.getByte("FL_ATIVO") : null
                	);
                result.add(perfil);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Variáveis de Perfil Informação carregados.");
            return result;
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Variáveis de Perfil Informação...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Variáveis de Perfil Informação...");
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
