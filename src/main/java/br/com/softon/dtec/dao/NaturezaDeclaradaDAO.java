package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.NATUREZA_DECLARADA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoNaturezasDeclaradas;
import br.com.softon.dtec.entity.NaturezaDeclarada;

public class NaturezaDeclaradaDAO {

    private final static Logger log = LoggerFactory.getLogger(NaturezaDeclaradaDAO.class);

    private static final String QUERY = carregarQuery(NATUREZA_DECLARADA, "query.sql");

    public static ConjuntoNaturezasDeclaradas getConjuntoNaturezasDeclaradas() throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<NaturezaDeclarada> result = new LinkedList<NaturezaDeclarada>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            rs = con.prepareStatement(QUERY).executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final NaturezaDeclarada naturezaDeclarada = new NaturezaDeclarada(
                		rs.getString("CD_NATUR_DECLR_OPER") != null ? rs.getInt("CD_NATUR_DECLR_OPER") : null,
        				rs.getString("DS_NATUR_DECLR_OPER") != null ? rs.getString("DS_NATUR_DECLR_OPER") : null,
        				rs.getString("DT_ATUALZ") != null ? rs.getTimestamp("DT_ATUALZ") : null,
        				rs.getString("VL_MEDIO_NATUR_OPER") != null ? rs.getDouble("VL_MEDIO_NATUR_OPER") : null
                	);
                result.add(naturezaDeclarada);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Naturezas Declaradas carregadas.");
            return new ConjuntoNaturezasDeclaradas(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de NaturezasDeclaradas...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de NaturezasDeclaradas...");
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
