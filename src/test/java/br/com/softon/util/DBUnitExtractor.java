package br.com.softon.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import br.com.softon.dtec.Configuracao;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

public class DBUnitExtractor {

    public static void main(String[] args) throws Exception {

        final String driver = Configuracao.props_conn.getProperty("driver");
        final String url = Configuracao.props_conn.getProperty("db_url");
        final String usr = Configuracao.props_conn.getProperty("username");
        final String pwd = Configuracao.props_conn.getProperty("password");

        Class.forName(driver);

        final Connection jdbcConnection = DriverManager.getConnection(url, usr, pwd);

        final IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        DatabaseConfig databaseConfig = connection.getConfig();
        databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);

        final ITableFilter filter = new DatabaseSequenceFilter(connection);
        final IDataSet dataset = new FilteredDataSet(filter, connection.createDataSet());

//        QueryDataSet partialDataSet = new QueryDataSet(connection);
//
//        partialDataSet.addTable("param.saftb004_lista_auxiliar");
//        partialDataSet.addTable("param.saftb005_operacao");
//        partialDataSet.addTable("param.saftb009_tipo_lista_auxiliar");
//        partialDataSet.addTable("param.saftb010_classe_lista_auxiliar");
//        partialDataSet.addTable("param.saftb011_transicao");
//        partialDataSet.addTable("param.saftb013_tipo_fraude");
//        partialDataSet.addTable("param.saftb042_conteudo_lista_axlr");
//        partialDataSet.addTable("param.saftb043_codificacao_transacao");
//        partialDataSet.addTable("param.saftb045_parametro_regra");
//        partialDataSet.addTable("param.saftb046_situacao");
//        partialDataSet.addTable("param.saftb048_tipo_parametro");
//        partialDataSet.addTable("param.saftb049_regra");
//        partialDataSet.addTable("param.saftb050_tipo_regra");
//        partialDataSet.addTable("param.saftb051_tipo_regra_log");
//        partialDataSet.addTable("param.saftb052_regra_log");
//        partialDataSet.addTable("param.saftb063_perfil_trnso_alerta");
//        partialDataSet.addTable("param.saftb097_posicao_contato");
//        partialDataSet.addTable("param.saftb106_tipo_operacao");
//        partialDataSet.addTable("param.saftb108_parametro_operacao");
//        partialDataSet.addTable("param.saftb109_regra_operacao");
//
//        partialDataSet.addTable("coorp.saftb025_lote");
//        partialDataSet.addTable("coorp.saftb026_sublote");
//
//        partialDataSet.addTable("analise.saftb006_alerta");
//        partialDataSet.addTable("analise.saftb008_alerta_historico");
//        partialDataSet.addTable("analise.saftb015_mudanca_situacao");
//        partialDataSet.addTable("analise.saftb018_mudanca_situacao_historico");
//        partialDataSet.addTable("analise.saftb031_parecer_historico");
//        partialDataSet.addTable("analise.saftb034_parecer");
//        partialDataSet.addTable("analise.saftb035_transacao_alerta");
//        partialDataSet.addTable("analise.saftb036_trnso_alerta_historico");
//        partialDataSet.addTable("analise.saftb044_acao");
//        partialDataSet.addTable("analise.saftb831_trnso_analise");

        new File("data").mkdirs();
        FlatDtdDataSet.write(dataset, new FileOutputStream("../data/sisaf.dtd"));

        final FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream("../data/sisaf.xml"));
        datasetWriter.setDocType("sisaf.dtd");
        datasetWriter.write(dataset);

        final QueryDataSet regrasDataSet = new QueryDataSet(connection);

        regrasDataSet.addTable("param.saftb049_regra");

        XmlDataSet.write(regrasDataSet, new FileOutputStream("../data/regras.xml"));
    }

}
