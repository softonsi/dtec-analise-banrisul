package br.com.softon.dtec.dao;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static br.com.softon.dtec.utils.CarregarQuery.Origem.LISTA_AUXILIAR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.db.ConnectionPool;
import br.com.softon.dtec.entity.ConjuntoListaAuxiliar;
import br.com.softon.dtec.entity.ListaAuxiliar;
import br.com.softon.dtec.entity.Sublote;

public class ListaAuxiliarDAO implements Configuracao {

    private final static Logger log = LoggerFactory.getLogger(ListaAuxiliarDAO.class);

    private static final String QUERY = carregarQuery(LISTA_AUXILIAR, "query.sql");

    // Caso exista lista Dow Jones, esta query será usada no lugar da query default.
    private static final String QUERY_LISTA_DJ = carregarQuery(LISTA_AUXILIAR, "query_lista_dj.sql");

    // Quando buscar lista auxiliar no Mercantil, esta query será usada no lugar da query default.
    private static final String QUERY_LISTA_MB = carregarQuery(LISTA_AUXILIAR, "query_lista_mb.sql");

    public static ConjuntoListaAuxiliar getConjuntoListaAuxiliar(Sublote sublote) throws Exception {
    	
    	boolean listaDJ = props_conf.getProperty("listaDJ") == null ? false : 
    		(props_conf.getProperty("listaDJ").equalsIgnoreCase("true") ? true : false);
    	
    	boolean listaMB = props_conf.getProperty("listaMB") == null ? false : 
    		(props_conf.getProperty("listaMB").equalsIgnoreCase("true") ? true : false);
    	
        log.debug("Construindo query...");
        Connection con = null;
        ResultSet rs = null;
        Collection<ListaAuxiliar> result = new LinkedList<ListaAuxiliar>();
        final PreparedStatement ps;

        try {
            log.debug("Buscando a conexão...");
            con = ConnectionPool.getConnection();
            log.debug("Conexão configurada, preparando execução da query...");
            if(listaDJ) {
                ps = con.prepareStatement(QUERY_LISTA_DJ);   
                ps.setLong(1, sublote.getCoLote());
                ps.setShort(2, sublote.getCoSublote());         	
            } else if(listaMB) {
                ps = con.prepareStatement(QUERY_LISTA_MB);   
                ps.setLong(1, sublote.getCoLote());
                ps.setShort(2, sublote.getCoSublote()); 
                ps.setLong(3, sublote.getCoLote());
                ps.setShort(4, sublote.getCoSublote()); 
                ps.setLong(5, sublote.getCoLote());
                ps.setShort(6, sublote.getCoSublote()); 
                ps.setLong(7, sublote.getCoLote());
                ps.setShort(8, sublote.getCoSublote());         	
            } else {
                ps = con.prepareStatement(QUERY);            	
            }
            rs = ps.executeQuery();
            log.debug("Query executada, buscando resultados da query...");
            while (rs.next()) {
                final ListaAuxiliar listaAuxiliar = new ListaAuxiliar(
                        rs.getString("ds_conteudo"),
                        rs.getString("cd_tp_lista_auxiliar") != null ? rs.getByte("cd_tp_lista_auxiliar") : null,
                        rs.getString("cd_classe_lista_auxiliar") != null ? rs.getByte("cd_classe_lista_auxiliar") : null,
                        rs.getString("nm_lista_auxiliar"),
                        rs.getShort("cd_lista_auxiliar"),
                        rs.getString("nm_motivo"),
                        rs.getString("CD_TRANSACAO"));

                result.add(listaAuxiliar);
            }
            log.debug("Fechando resultados da query executada...");
            rs.close();
            log.debug("Resultado da query fechado.");
            if (result.isEmpty()) {
                return null;
            }
            log.debug(result.size() + " Listas Auxiliares carregadas.");
            return new ConjuntoListaAuxiliar(result);
        } catch (SQLException e) {
            log.error("Houve um erro ao buscar dados de Lista Auxiliar...");
            throw e;
        } catch (Exception e) {
            log.error("Houve um erro ao buscar dados de Lista Auxiliar...");
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
