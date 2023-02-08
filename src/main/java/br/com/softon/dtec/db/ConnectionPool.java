package br.com.softon.dtec.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;

public final class ConnectionPool implements Configuracao {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);
	private static final long BATCH_SIZE_PADRAO = 1000;
    private static DataSource ds = null;
    
    public static long BATCH_SIZE;
    
    static {
    	try {
    		BATCH_SIZE = Long.parseLong(props_conn.getProperty("batch_size"));
    	} catch(Exception e) {
    		LOG.warn("Não foi encontrada configuração para tamanho máximo de batch. Utilizando o valor padrão ("+BATCH_SIZE_PADRAO+").");
    	} finally {
    		if(BATCH_SIZE == 0)
    			BATCH_SIZE = BATCH_SIZE_PADRAO;
    	}
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() throws Exception {
        Connection con = null;
        try {
            if (ConnectionPool.ds == null) {
                configurarConexao();
            }
            con = ds.getConnection();
        } catch (SQLException e) {
            LOG.error("Erro 001 - Problemas com a conexão ao banco de dados.", e);
            System.exit(1);
        } catch (Exception e) {
            LOG.error("Houve um erro desconhecido ao conectar ao banco de dados.");
            throw e;
        }
        return con;
    }

    private static void configurarConexao() throws SQLException, Exception {
        final String driver;
        final String url;
        final String usr;
        final String pwd;

        try {
            driver = props_conn.getProperty("driver");
            url = props_conn.getProperty("db_url");
            usr = props_conn.getProperty("username");
            pwd = props_conn.getProperty("password");

            Class.forName(driver).newInstance();
        } catch (Exception e) {
            throw e;
        }

        LOG.debug("Tentando conexão com o banco de dados...");
        try {
            ConnectionPool.ds = criarDataSource(url, usr, pwd);
            ConnectionPool.ds.getConnection();
            LOG.debug("Conexão com banco de dados efetuado com sucesso.");
        } catch (SQLException e) {
            LOG.error("Houve um erro ao buscar sublote disponível para processamento...");
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public static DataSource criarDataSource(String uri, String username, String password)
            throws Exception {

        GenericObjectPool connectionPool = new GenericObjectPool(null);

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(uri, username, password);

        new PoolableConnectionFactory(connectionFactory, connectionPool, null, "SELECT 1", false, true);

        return new PoolingDataSource(connectionPool);
    }
}
