package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.ELEMENTOS_COMUNS_CONTA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoElementosComunsConta;
import br.com.softon.dtec.entity.ElementosComunsConta;
import br.com.softon.dtec.entity.Sublote;

public class ElementosComunsContaDAO {

    private final static Logger log = LoggerFactory.getLogger(ElementosComunsContaDAO.class);

    private static final String QUERY = carregarQuery(ELEMENTOS_COMUNS_CONTA, "query.sql");

    public static ConjuntoElementosComunsConta getConjuntoElementosComunsConta(Sublote sublote) throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<ElementosComunsConta> result = new LinkedList<ElementosComunsConta>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            final PreparedStatement ps = con.prepareStatement(QUERY);
            ps.setLong(1, sublote.getCoLote());
            ps.setShort(2, sublote.getCoSublote());
            rs = ps.executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final ElementosComunsConta contrato = new ElementosComunsConta(
                		rs.getString("CD_TRANSACAO") != null ? rs.getString("CD_TRANSACAO") : null
                	);
                result.add(contrato);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Elementos Comuns carregados.");
            return new ConjuntoElementosComunsConta(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Elementos Comuns...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Elementos Comuns...");
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
