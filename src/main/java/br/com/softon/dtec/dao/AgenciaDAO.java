package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.AGENCIA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.Agencia;
import br.com.softon.dtec.entity.ConjuntoAgencias;

public class AgenciaDAO {

    private final static Logger log = LoggerFactory.getLogger(AgenciaDAO.class);

    private static final String QUERY = carregarQuery(AGENCIA, "query.sql");

    /**
     * Método responsável por popular objeto de Agência para uso nas regras.
     * @return Conjunto de Agências
     * @throws Exception
     */
    public static ConjuntoAgencias getConjuntoAgencias() throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<Agencia> result = new LinkedList<Agencia>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            rs = con.prepareStatement(QUERY).executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final Agencia agencia = new Agencia(
                        rs.getString("CD_AG") != null ? rs.getInt("CD_AG") : null,
                        rs.getString("CD_BANCO") != null ? rs.getString("CD_BANCO") : null,
                        rs.getString("CD_DIG_AG") != null ? rs.getString("CD_DIG_AG") : null,
                        rs.getString("CD_SUREG") != null ? rs.getInt("CD_SUREG") : null,
                        rs.getString("CD_USU_RESP") != null ? rs.getString("CD_USU_RESP") : null,
                        rs.getString("FL_FRONT") != null ? rs.getByte("FL_FRONT") : null,
                        rs.getString("FL_LOCAL_PUBLIC") != null ? rs.getByte("FL_LOCAL_PUBLIC") : null,
                        rs.getString("FL_REG_BX_RENDA") != null ? rs.getByte("FL_REG_BX_RENDA") : null,
                        rs.getString("FL_REG_CRIME") != null ? rs.getByte("FL_REG_CRIME") : null,
                        rs.getString("NM_AG") != null ? rs.getString("NM_AG") : null,
                        rs.getString("NM_CID") != null ? rs.getString("NM_CID") : null,
                        rs.getString("SG_UF_AG") != null ? rs.getString("SG_UF_AG") : null
                	);
                result.add(agencia);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Agencias carregadas.");
            return new ConjuntoAgencias(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Agências...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Agências...");
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
