package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.CADASTRO_CLIENTE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.CadastroCliente;
import br.com.softon.dtec.entity.ConjuntoCadastroClientes;
import br.com.softon.dtec.entity.Sublote;

public class CadastroClienteDAO {

    private final static Logger log = LoggerFactory.getLogger(CadastroClienteDAO.class);

    private static final String QUERY = carregarQuery(CADASTRO_CLIENTE, "query.sql");

    public static ConjuntoCadastroClientes getConjuntoCadastroClientes(Sublote sublote) throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<CadastroCliente> result = new LinkedList<CadastroCliente>();
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
                final CadastroCliente cadastroCliente = new CadastroCliente(
                		rs.getString("CD_CEP_RESID") != null ? rs.getInt("CD_CEP_RESID") : null,
        				rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null,
        				rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getByte("CD_TP_IDENTF_CLIE") : null,
        				rs.getString("CD_TP_PESSOA") != null ? rs.getString("CD_TP_PESSOA").toCharArray()[0] : null,
        				rs.getString("NM_CLIE") != null ? rs.getString("NM_CLIE") : null,
        				rs.getString("NM_CONJUGE") != null ? rs.getString("NM_CONJUGE") : null,
        				rs.getString("NM_ENDER_RESID") != null ? rs.getString("NM_ENDER_RESID") : null,
                		rs.getString("NM_CID_RESID") != null ? rs.getString("NM_CID_RESID") : null,
                        rs.getString("SG_UF_RESID") != null ? rs.getString("SG_UF_RESID") : null,
                        rs.getString("CD_PAIS_RESID") != null ? rs.getInt("CD_PAIS_RESID") : null,
        				rs.getString("NM_MAE") != null ? rs.getString("NM_MAE") : null,
        				rs.getString("NM_PAI") != null ? rs.getString("NM_PAI") : null
                	);
                result.add(cadastroCliente);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            ps.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Cadastro de Clientes carregadas.");
            return new ConjuntoCadastroClientes(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Cadastro de Clientes...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Cadastro de Clientes...");
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
