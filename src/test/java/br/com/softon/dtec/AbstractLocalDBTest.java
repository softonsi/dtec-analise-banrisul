package br.com.softon.dtec;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLocalDBTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractLocalDBTest.class);
    private static final String DATA_SISAF_XML = "../data/sisaf.xml";
    public static final String DATA_H2SCHEMA_DDL = "../data/h2schema.ddl";
    public static final String H2_PATH_TO_FILE = "../data/test";
    public static final String JDBC_H2_DATA_TEST = "jdbc:h2:" + H2_PATH_TO_FILE;
    public static final String H2_USER = "sa";
    public static final String H2_PASSWORD = "";
    public static final String H2_DRIVER = org.h2.Driver.class.getName();

    @Before
    public void setup() throws SQLException, DatabaseUnitException, ClassNotFoundException, IOException {

        if (!new File(DATA_H2SCHEMA_DDL).exists()) {
            throw new RuntimeException("Schema not found.");
        }

        if (!new File(DATA_SISAF_XML).exists()) {
            throw new RuntimeException("DBUnit dataset not found.");
        }

        final File h2File = new File(H2_PATH_TO_FILE + ".h2.db");
        if (h2File.exists()) {
            h2File.delete();
        }

        final File h2FileLock = new File(H2_PATH_TO_FILE + ".lock.db");
        if (h2FileLock.exists()) {
            h2FileLock.delete();
        }

        LOGGER.error("INIT SETUP");

        Configuracao.props_conn.setProperty("username", H2_USER);
        Configuracao.props_conn.setProperty("password", H2_PASSWORD);
        Configuracao.props_conn.setProperty("db_url", JDBC_H2_DATA_TEST);
        Configuracao.props_conn.setProperty("driver", H2_DRIVER);

        if (!new File(H2_PATH_TO_FILE + ".zip").exists()) {
            LOGGER.error("START DBUNIT");
            final Connection connection2Cache = DriverManager.getConnection(JDBC_H2_DATA_TEST + ";LOG=0;CACHE_SIZE=65536;LOCK_MODE=0;UNDO_LOG=0", H2_USER, H2_PASSWORD);

            final Statement st = connection2Cache.createStatement();

            final String ddl = FileUtils.readFileToString(new File(DATA_H2SCHEMA_DDL));

            try {
                st.execute(ddl);
                LOGGER.error("DDL DONE");
            } catch (org.h2.jdbc.JdbcSQLException ex) {
                throw new RuntimeException(ex);
            }

            final IDatabaseConnection connection = new DatabaseConnection(connection2Cache);
            connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
            connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
            connection.getConfig().setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);

            final IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(DATA_SISAF_XML));

            try {
                DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
            } finally {
                connection2Cache.close();
            }

            org.h2.tools.Backup.execute(H2_PATH_TO_FILE + ".zip", "../data/", "test", false);

            LOGGER.error("END DBUNIT");
        }

        org.h2.tools.Restore.execute(H2_PATH_TO_FILE + ".zip", "../data/", "test", false);

        LOGGER.error("END SETUP");
    }
}
