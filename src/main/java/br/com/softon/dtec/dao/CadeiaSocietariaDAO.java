package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.CADEIA_SOCIETARIA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.CadeiaSocietaria;
import br.com.softon.dtec.entity.ConjuntoCadeiasSocietarias;

public class CadeiaSocietariaDAO {

    private final static Logger log = LoggerFactory.getLogger(CadeiaSocietariaDAO.class);

    private static final String QUERY = carregarQuery(CADEIA_SOCIETARIA, "query.sql");

    public static ConjuntoCadeiasSocietarias getConjuntoCadeiasSocietarias() throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<CadeiaSocietaria> result = new LinkedList<CadeiaSocietaria>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            rs = con.prepareStatement(QUERY).executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final CadeiaSocietaria cadeiaSocietaria = new CadeiaSocietaria(
                		rs.getString("CD_DOC_IDENTF_SOCIEDD") != null ? rs.getString("CD_DOC_IDENTF_SOCIEDD") : null,
        				rs.getString("CD_DOC_IDENTF_SOCIO") != null ? rs.getString("CD_DOC_IDENTF_SOCIO") : null,
        				rs.getString("CD_IDTFD_MOVTO") != null ? rs.getByte("CD_IDTFD_MOVTO") : null,
        				rs.getString("CD_TP_IDENTF_SOCIEDD") != null ? rs.getByte("CD_TP_IDENTF_SOCIEDD") : null,
        				rs.getString("CD_TP_IDENTF_SOCIO") != null ? rs.getByte("CD_TP_IDENTF_SOCIO") : null,
        				rs.getString("CD_TP_PARTC_SOCIO") != null ? rs.getShort("CD_TP_PARTC_SOCIO") : null,
        				rs.getString("NM_SOCIEDD") != null ? rs.getString("NM_SOCIEDD") : null,
        				rs.getString("NM_SOCIO") != null ? rs.getString("NM_SOCIO") : null
                	);                
                result.add(cadeiaSocietaria);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Cadeias Societarias carregadas.");
            return new ConjuntoCadeiasSocietarias(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Cadeias Societárias...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Cadeias Societárias...");
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
