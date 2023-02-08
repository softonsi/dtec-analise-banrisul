package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.MEDIA_RAMO_ATIVIDADE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoMediaRamosAtividades;
import br.com.softon.dtec.entity.MediaRamoAtividade;

public class MediaRamoAtividadeDAO {

    private final static Logger log = LoggerFactory.getLogger(MediaRamoAtividadeDAO.class);

    private static final String QUERY = carregarQuery(MEDIA_RAMO_ATIVIDADE, "query.sql");

    public static ConjuntoMediaRamosAtividades getConjuntoMediaRamosAtividades() throws Exception {
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<MediaRamoAtividade> result = new LinkedList<MediaRamoAtividade>();

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            rs = con.prepareStatement(QUERY).executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final MediaRamoAtividade mediaRamoAtividade = new MediaRamoAtividade(
                        rs.getString("CD_RAMO_ATIVID") != null ? rs.getString("CD_RAMO_ATIVID") : null,
                        rs.getString("DT_CALCULO") != null ? rs.getTimestamp("DT_CALCULO") : null,
                        rs.getString("VL_MED_CREDITO") != null ? rs.getDouble("VL_MED_CREDITO") : null,
                        rs.getString("VL_MED_DEBITO") != null ? rs.getDouble("VL_MED_DEBITO") : null 
                	);
                result.add(mediaRamoAtividade);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Media Ramos Atividades carregadas.");
            return new ConjuntoMediaRamosAtividades(result);
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
