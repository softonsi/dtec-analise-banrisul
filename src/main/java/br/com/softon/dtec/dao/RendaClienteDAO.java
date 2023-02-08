package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.RENDA_CLIENTE;

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
import br.com.softon.dtec.entity.ConjuntoRendasClientes;
import br.com.softon.dtec.entity.RendaCliente;
import br.com.softon.dtec.entity.Sublote;

public class RendaClienteDAO {

    private final static Logger log = LoggerFactory.getLogger(RendaClienteDAO.class);

    private static final String QUERY = carregarQuery(RENDA_CLIENTE, "query.sql");

    public static ConjuntoRendasClientes getConjuntoRendasClientes(Sublote sublote) throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Set<RendaCliente> result = new HashSet<RendaCliente>();

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
                final RendaCliente rendaCliente = new RendaCliente(
                        rs.getString("CD_CEP_TRAB") != null ? rs.getInt("CD_CEP_TRAB") : null,
                        rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null,
                        rs.getString("CD_DOC_IDENTF_TRAB") != null ? rs.getString("CD_DOC_IDENTF_TRAB") : null,
                        rs.getString("CD_FONE_COM") != null ? rs.getString("CD_FONE_COM") : null,
                        rs.getString("CD_LOTE") != null ? rs.getLong("CD_LOTE") : null,
                        rs.getString("CD_OCUP") != null ? rs.getLong("CD_OCUP") : null,
                        rs.getString("CD_PAIS_TRAB") != null ? rs.getShort("CD_PAIS_TRAB") : null,
                        rs.getString("CD_SEQ_OCUP") != null ? rs.getInt("CD_SEQ_OCUP") : null,
                        rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getByte("CD_TP_IDENTF_CLIE") : null,
                        rs.getString("CD_TP_IDENTF_TRAB") != null ? rs.getByte("CD_TP_IDENTF_TRAB") : null,
                        rs.getString("CD_TP_PESSOA") != null ? rs.getString("CD_TP_PESSOA").toCharArray()[0] : null,
                        rs.getString("CD_TP_PESSOA_TRAB") != null ? rs.getString("CD_TP_PESSOA_TRAB").toCharArray()[0] : null,
                        rs.getString("DT_FIM_ATIVID") != null ? rs.getTimestamp("DT_FIM_ATIVID") : null,
                        rs.getString("DT_INIC_ATIVID") != null ? rs.getTimestamp("DT_INIC_ATIVID") : null,
                        rs.getString("DT_ULTM_ATULZ_CADSTRL") != null ? rs.getTimestamp("DT_ULTM_ATULZ_CADSTRL") : null,
                        rs.getString("FL_RENDA_COMPROV") != null ? rs.getByte("FL_RENDA_COMPROV") : null,
                        rs.getString("NM_CID_TRAB") != null ? rs.getString("NM_CID_TRAB") : null,
                        rs.getString("NM_EMP_TRAB") != null ? rs.getString("NM_EMP_TRAB") : null,
                        rs.getString("NM_ENDER_TRAB") != null ? rs.getString("NM_ENDER_TRAB") : null,
                        rs.getString("NM_FANT") != null ? rs.getString("NM_FANT") : null,
                        rs.getString("SG_UF_TRAB") != null ? rs.getString("SG_UF_TRAB") : null,
                        rs.getString("VL_RENDA") != null ? rs.getDouble("VL_RENDA") : null
                	);

                result.add(rendaCliente);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Rendas de Clientes carregadas.");
            return new ConjuntoRendasClientes(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Média de Ramos de Atividades...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Média de Ramos de Atividades...");
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
