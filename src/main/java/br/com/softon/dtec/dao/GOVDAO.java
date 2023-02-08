package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.GOV;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoGOVs;
import br.com.softon.dtec.entity.GOV;
import br.com.softon.dtec.entity.Sublote;

public class GOVDAO {

    private final static Logger log = LoggerFactory.getLogger(GOVDAO.class);

    private static final String QUERY = carregarQuery(GOV, "query.sql");

    public static ConjuntoGOVs getConjuntoGOVs(Sublote sublote) throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<GOV> result = new LinkedList<GOV>();
        final PreparedStatement ps;

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            ps = con.prepareStatement(QUERY);
            ps.setLong(1, sublote.getCoLote());
            ps.setShort(2, sublote.getCoSublote());
            rs = ps.executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final GOV gov = new GOV(
                		rs.getString("NU_CPF_CNPJ") != null ? rs.getString("NU_CPF_CNPJ") : null
                	);                
                result.add(gov);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " GOVs carregados.");
            return new ConjuntoGOVs(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de GOV...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de GOV...");
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
