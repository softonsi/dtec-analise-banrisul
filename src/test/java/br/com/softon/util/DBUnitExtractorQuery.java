package br.com.softon.util;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import br.com.softon.dtec.Configuracao;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import static br.com.softon.dtec.utils.CarregarQuery.Origem.*;
import static br.com.softon.dtec.utils.CarregarQuery.*;

public class DBUnitExtractorQuery {

    public static void main(String[] args) throws Exception {

        final String driver = Configuracao.props_conn.getProperty("driver");
        final String url = Configuracao.props_conn.getProperty("db_url");
        final String usr = Configuracao.props_conn.getProperty("username");
        final String pwd = Configuracao.props_conn.getProperty("password");

        Class.forName(driver);

        final Connection jdbcConnection = DriverManager.getConnection(url, usr, pwd);

        final IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("RESULTADO", carregarQuery(TESTE, "query_validacao.sql"));
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("../data/output_FILE_AQUI.xml"));
    }

}
