package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.PPE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoPPEs;
import br.com.softon.dtec.entity.PPE;
import br.com.softon.dtec.entity.Sublote;

public class PPEDAO {

    private final static Logger log = LoggerFactory.getLogger(PPEDAO.class);

    private static final String QUERY = carregarQuery(PPE, "query.sql");

    public static ConjuntoPPEs getConjuntoPPEs(Sublote sublote) throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<PPE> result = new LinkedList<PPE>();
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
                final PPE ppe = new PPE(
                		rs.getString("CD_DOC_IDENTF") != null ? rs.getString("CD_DOC_IDENTF") : null,
        				rs.getString("CD_FONTE_INF") != null ? rs.getByte("CD_FONTE_INF") : null,
        				rs.getString("CD_PAIS_ORIG") != null ? rs.getShort("CD_PAIS_ORIG") : null,
        				rs.getString("CD_SEQ_PPE") != null ? rs.getInt("CD_SEQ_PPE") : null,
        				rs.getString("CD_TP_IDENTF") != null ? rs.getByte("CD_TP_IDENTF") : null,
        				rs.getString("DS_PPE_GRAU_ENVOLV") != null ? rs.getString("DS_PPE_GRAU_ENVOLV") : null,
        				rs.getString("DT_ATULZ_PPE") != null ? rs.getTimestamp("DT_ATULZ_PPE") : null,
        				rs.getString("NM_PPE") != null ? rs.getString("NM_PPE") : null,
        				rs.getString("NU_PASSRTE") != null ? rs.getString("NU_PASSRTE") : null,
        				rs.getString("SG_ORG_INTERNL") != null ? rs.getString("SG_ORG_INTERNL") : null
                	);                
                result.add(ppe);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " PPEs carregados.");
            return new ConjuntoPPEs(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de PPE...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de PPE...");
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
