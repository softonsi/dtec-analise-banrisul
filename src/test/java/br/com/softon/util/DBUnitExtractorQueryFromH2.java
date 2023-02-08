package br.com.softon.util;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import br.com.softon.dtec.AbstractLocalDBTest;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import static br.com.softon.dtec.utils.CarregarQuery.Origem.*;
import static br.com.softon.dtec.utils.CarregarQuery.*;

public class DBUnitExtractorQueryFromH2 {

    public static void main(String[] args) throws Exception {

        final String driver = AbstractLocalDBTest.H2_DRIVER;
        final String url = AbstractLocalDBTest.JDBC_H2_DATA_TEST;
        final String usr = AbstractLocalDBTest.H2_USER;
        final String pwd = AbstractLocalDBTest.H2_PASSWORD;

        Class.forName(driver);

        final Connection jdbcConnection = DriverManager.getConnection(url, usr, pwd);

        final IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("RESULTADO", carregarQuery(TESTE, "query_validacao.sql"));
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("../data/output_FILE_AQUI.xml"));
    }

}
