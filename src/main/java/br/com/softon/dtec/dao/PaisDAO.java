package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.PAIS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoPaises;
import br.com.softon.dtec.entity.Pais;

public class PaisDAO {

    private final static Logger log = LoggerFactory.getLogger(PaisDAO.class);

    private static final String QUERY = carregarQuery(PAIS, "query.sql");

    public static ConjuntoPaises getConjuntoPaises() throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<Pais> result = new LinkedList<Pais>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            rs = con.prepareStatement(QUERY).executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final Pais pais = new Pais(
                		rs.getString("CD_PAIS") != null ? rs.getShort("CD_PAIS") : null,
        				rs.getString("FL_ELEV_RELAC") != null ? rs.getByte("FL_ELEV_RELAC") : null,
        				rs.getString("FL_FONTE_NARCO") != null ? rs.getByte("FL_FONTE_NARCO") : null,
        				rs.getString("FL_FRONT") != null ? rs.getByte("FL_FRONT") : null,
        				rs.getString("FL_FRONT_COMUM") != null ? rs.getByte("FL_FRONT_COMUM") : null,
        				rs.getString("FL_PARAIS_FISCAL") != null ? rs.getByte("FL_PARAIS_FISCAL") : null,
        				rs.getString("FL_PROXMDD_ETNICA") != null ? rs.getByte("FL_PROXMDD_ETNICA") : null,
        				rs.getString("FL_PROXMDD_LINGTCA") != null ? rs.getByte("FL_PROXMDD_LINGTCA") : null,
        				rs.getString("FL_PROXMDD_POLITICA") != null ? rs.getByte("FL_PROXMDD_POLITICA") : null,
        				rs.getString("FL_SUSP") != null ? rs.getByte("FL_SUSP") : null,
        				rs.getString("NM_PAIS") != null ? rs.getString("NM_PAIS") : null
                	);
                result.add(pais);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Paises carregados.");
            return new ConjuntoPaises(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Paises...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Paises...");
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
