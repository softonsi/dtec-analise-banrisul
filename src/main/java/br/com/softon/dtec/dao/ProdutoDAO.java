package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.PRODUTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoProdutos;
import br.com.softon.dtec.entity.Produto;

public class ProdutoDAO {

    private final static Logger log = LoggerFactory.getLogger(ProdutoDAO.class);

    private static final String QUERY = carregarQuery(PRODUTO, "query.sql");

    public static ConjuntoProdutos getConjuntoProdutos() throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<Produto> result = new LinkedList<Produto>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            rs = con.prepareStatement(QUERY).executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final Produto produto = new Produto(
                		rs.getString("CD_PRODUTO") != null ? rs.getString("CD_PRODUTO") : null,
        				rs.getString("FL_BX_LIQUIDEZ") != null ? rs.getByte("FL_BX_LIQUIDEZ") : null,
        				rs.getString("FL_BX_RENTABILIDADE") != null ? rs.getByte("FL_BX_RENTABILIDADE") : null,
        				rs.getString("NM_PRODUTO") != null ? rs.getString("NM_PRODUTO") : null,
        				rs.getString("VL_MED_INVEST") != null ? rs.getDouble("VL_MED_INVEST") : null,
        				rs.getString("VL_MERCADO") != null ? rs.getDouble("VL_MERCADO") : null
                	);                
                result.add(produto);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Produtos carregados.");
            return new ConjuntoProdutos(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Produtos...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Produtos...");
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
