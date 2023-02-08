package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.TRANSMISSAO_ORDEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoTransmissoesOrdem;
import br.com.softon.dtec.entity.Sublote;
import br.com.softon.dtec.entity.TransmissaoOrdem;

public class TransmissaoOrdemDAO {

    private final static Logger log = LoggerFactory.getLogger(TransmissaoOrdemDAO.class);

    private static final String QUERY = carregarQuery(TRANSMISSAO_ORDEM, "query.sql");

    public static ConjuntoTransmissoesOrdem getConjuntoTransmissoesOrdem(Sublote sublote) throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<TransmissaoOrdem> result = new LinkedList<TransmissaoOrdem>();

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
                final TransmissaoOrdem contrato = new TransmissaoOrdem(
                		rs.getString("CD_DOC_IDENTF_REPRE") != null ? rs.getString("CD_DOC_IDENTF_REPRE") : null,
        				rs.getString("CD_TP_IDENTF_REPRE") != null ? rs.getByte("CD_TP_IDENTF_REPRE") : null,
        				rs.getString("CD_DOC_IDENTF_CLIE") != null ? rs.getString("CD_DOC_IDENTF_CLIE") : null,
						rs.getString("CD_TP_IDENTF_CLIE") != null ? rs.getByte("CD_TP_IDENTF_CLIE") : null,
                		rs.getString("QT_REPRESENTACAO") != null ? rs.getInt("QT_REPRESENTACAO") : null
                	);
                result.add(contrato);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " TransmissãoOrdem carregados.");
            return new ConjuntoTransmissoesOrdem(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Contratos...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Contratos...");
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
