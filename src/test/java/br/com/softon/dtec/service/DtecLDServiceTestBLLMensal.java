package br.com.softon.dtec.service;

import static br.com.softon.dtec.service.DtecLDService.ModoExecucao.TESTE;
import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.utils.CarregarQuery;
import br.com.softon.dtec.utils.CarregarQuery.Origem;
import softonPack.util.PropertieHandle;

public class DtecLDServiceTestBLLMensal {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(DtecLDServiceTestBLLMensal.class);

	private Connection jdbcConnection;

	private BufferedReader br;
	private BufferedWriter bw;

	@Before
	public void setup() throws SQLException, DatabaseUnitException,
			ClassNotFoundException, IOException, InterruptedException {
		LOGGER.info("INIT SETUP");

		PropertieHandle.setTeste(true);

		final String driver = "oracle.jdbc.driver.OracleDriver";
		final String url = "jdbc:oracle:thin:@10.10.10.21:1521/orcl";
		final String usr = "DTEC_LDCRV4_BANRISUL_20200925";
		final String pwd = "softon";
		
		Configuracao.props_conn.setProperty("driver", driver);
		Configuracao.props_conn.setProperty("db_url", url);
		Configuracao.props_conn.setProperty("username", usr);
		Configuracao.props_conn.setProperty("password", pwd);
		Configuracao.props_conn.setProperty("banco", "ORACLE");

		Configuracao.props_conf.setProperty("listaDJ", "false");

		Class.forName(driver);

		jdbcConnection = DriverManager.getConnection(url, usr, pwd);

		// Limpar os testes anteriores...
		limpaTeste();


		Writer writer = new FileWriter("regras-renner/update_renner.sql");
		bw = new BufferedWriter(writer);

	/*	updateRegra("regras-renner/REGRA 01.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra001.sql"));
		updateRegra("regras-renner/REGRA 02.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra002.sql"));
		updateRegra("regras-renner/REGRA 03.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra003.sql"));
		updateRegra("regras-renner/REGRA 04.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra004.sql"));
		updateRegra("regras-renner/REGRA 05.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra005.sql"));
		updateRegra("regras-renner/REGRA 06.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra006.sql"));
		updateRegra("regras-renner/REGRA 07.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra007.sql"));
		
		updateRegra("regras-renner/REGRA 09.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra009.sql"));
		updateRegra("regras-renner/REGRA 10.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra010.sql"));
		updateRegra("regras-renner/REGRA 11.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra011.sql"));
		updateRegra("regras-renner/REGRA 12.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra012.sql"));
	    updateRegra("regras-renner/REGRA 20.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra020.sql"));
		updateRegra("regras-renner/REGRA 21.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra021.sql"));
//		updateRegra("regras-renner/REGRA 22.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra022.sql"));
		updateRegra("regras-renner/REGRA 23.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra023.sql"));
//		updateRegra("regras-renner/REGRA 24.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra024.sql"));
		updateRegra("regras-renner/REGRA 25.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra025.sql"));
		updateRegra("regras-renner/REGRA 26.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra026.sql"));
		updateRegra("regras-renner/REGRA 27.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra027.sql"));
		updateRegra("regras-renner/REGRA 30.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra030.sql"));
		updateRegra("regras-renner/REGRA 31.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra031.sql"));
		updateRegra("regras-renner/REGRA 32.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra032.sql"));
		updateRegra("regras-renner/REGRA 33.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra033.sql"));
		updateRegra("regras-renner/REGRA 41.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra041.sql"));
		updateRegra("regras-renner/REGRA 45.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra045.sql"));
		updateRegra("regras-renner/REGRA 46.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra046.sql"));
		updateRegra("regras-renner/REGRA 47.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra047.sql"));
		updateRegra("regras-renner/REGRA 48.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra048.sql"));
		updateRegra("regras-renner/REGRA 49.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra049.sql"));
		updateRegra("regras-renner/REGRA 50.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra050.sql"));
		updateRegra("regras-renner/REGRA 51.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra051.sql"));
		updateRegra("regras-renner/REGRA 52.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra052.sql"));
		updateRegra("regras-renner/REGRA 53.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra053.sql"));
		updateRegra("regras-renner/REGRA 54.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra054.sql")); */
		updateRegra("regras-renner/REGRA 55.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra055.sql"));
		updateRegra("regras-renner/REGRA 56.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra056.sql"));
/*		updateRegra("regras-renner/REGRA 58.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra058.sql"));
		updateRegra("regras-renner/REGRA 60.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra060.sql"));
		updateRegra("regras-renner/REGRA 61.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra061.sql"));
		updateRegra("regras-renner/REGRA 63.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra063.sql"));
		updateRegra("regras-renner/REGRA 68.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra068.sql"));
		updateRegra("regras-renner/REGRA 69.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra069.sql"));
		updateRegra("regras-renner/REGRA 71.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra071.sql"));
		updateRegra("regras-renner/REGRA 73.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra073.sql"));
		updateRegra("regras-renner/REGRA 74.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra074.sql"));
		updateRegra("regras-renner/REGRA 75.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra075.sql"));
		updateRegra("regras-renner/REGRA 90.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra090.sql"));
		updateRegra("regras-renner/REGRA 91.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra091.sql"));
		updateRegra("regras-renner/REGRA 93.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra093.sql"));
		updateRegra("regras-renner/REGRA 101.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra101.sql"));
		updateRegra("regras-renner/REGRA 102.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra102.sql"));
		updateRegra("regras-renner/REGRA 110.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra110.sql"));
		updateRegra("regras-renner/REGRA 120.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra120.sql"));
		updateRegra("regras-renner/REGRA 124.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra124.sql"));
		updateRegra("regras-renner/REGRA 125.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra125.sql"));
		updateRegra("regras-renner/REGRA 126.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra126.sql"));
		updateRegra("regras-renner/REGRA 160.drl",carregarQuery(CarregarQuery.Origem.TESTE, "regra160.sql"));*/
        
		bw.flush();
		bw.close();
		
		final Statement statement = jdbcConnection.createStatement();
		String query = carregarQuery(CarregarQuery.Origem.TESTE, "setup.sql");

		for (int i = 0; i < query.trim().split(";").length; i++) {
			statement.addBatch(query.trim().split(";")[i]);
		}
		statement.executeBatch();

		Runtime.getRuntime().exec("redis/redis-server");

		// Pausar a Thread por 1 segundo para dar tempo do Redis ser ligado...
		Thread.sleep(1000);

		LOGGER.info("END SETUP");
	}

	private void updateRegra(String regra, String query) {
		try {
			File file = new File(regra);
			Reader reader = new FileReader(file);
			br = new BufferedReader(reader);
			String nextLine = "";
			StringBuffer sb = new StringBuffer();
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine);
			}

			final PreparedStatement ps = jdbcConnection.prepareStatement(query);
			ps.setString(1, sb.toString());

			ps.execute();

			bw.append(query.replace("?", "'" + sb.toString() + "'") + ";\n");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testeBRB() throws DatabaseUnitException, MalformedURLException,
			SQLException {
		final IDatabaseConnection connection = new DatabaseConnection(
				jdbcConnection);
		final DtecLDService service = new DtecLDService();
		service.execute(TESTE);

		{
			executaAsserts(connection);
		}

		jdbcConnection.close();
	}

	private void executaAsserts(final IDatabaseConnection connection)
			throws AmbiguousTableNameException, DatabaseUnitException,
			DataSetException {

		QueryDataSet resultadoEsperadoTeste = null;
		QueryDataSet resultadoEsperadoValidacao = null;
		String[] queries = null;

		// // Regra 1
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 1 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 2
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 2 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 3
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 3 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 4
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 4 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 5
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 5 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 6
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 6 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 7
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 7 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		
		// // Regra 9
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 9 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 10
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 10 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 11
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 11 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
			
		// // Regra 12
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 12 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 20
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 20 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 21
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 21 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
//		 // Regra 22
//		 resultadoEsperadoTeste = new QueryDataSet(connection);
//		 resultadoEsperadoValidacao = new QueryDataSet(connection);
//		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 22 (Ora).sql").split(";");
//		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
//		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
//		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
//		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 23
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 23 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

//		 // Regra 24
//		 resultadoEsperadoTeste = new QueryDataSet(connection);
//		 resultadoEsperadoValidacao = new QueryDataSet(connection);
//		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 24 (Ora).sql").split(";");
//		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
//		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
//		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
//		 resultadoEsperadoTeste.getTable("RESULTADO"));

			// // Regra 25
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 25 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

			// // Regra 26
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 26 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

			// // Regra 27
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 27 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		  
		 	// // Regra 30
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 30 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 
			// // Regra 31
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 31 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

			// // Regra 32
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 32 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

			// // Regra 33
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 33 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 
		 	// // Regra 41
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 41 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
//		 // Regra 45
//		 resultadoEsperadoTeste = new QueryDataSet(connection);
//		 resultadoEsperadoValidacao = new QueryDataSet(connection);
//		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 45 (Ora).sql").split(";");
//		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
//		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
//		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
//		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 46
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 46 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
//		 // Regra 47
//		 resultadoEsperadoTeste = new QueryDataSet(connection);
//		 resultadoEsperadoValidacao = new QueryDataSet(connection);
//		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 47 (Ora).sql").split(";");
//		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
//		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
//		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
//		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 48
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 48 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 49
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 49 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 50 
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 50 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 51 
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 51 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 52 
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 52 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 53 
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 53 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 54 
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 54 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 58 
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 58 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

		// // Regra 60
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 60 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
			
		// // Regra 61
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 61 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
			
		// // Regra 63
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 63 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
			 
		 		 
		// // Regra 68
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 68 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 69
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 69 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

		// // Regra 71
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 71 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));		  
 
		// // Regra 73
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 73 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 
		// // Regra 74
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 74 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 
		// // Regra 75
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 75 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 		 
		// // Regra 90
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 90 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 91
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 91 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
    	// // Regra 93
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 93 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 101
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 101 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 102
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 102 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 110
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 110 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 120
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 120 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

		 // Regra 124
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 124 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

//		 // Regra 125
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 125 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

		// // Regra 126
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 126 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 
		 
		// // Regra 160
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 160 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
//		
		// // Regra 170
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 170 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 171
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 171 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		
		// // Regra 172
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 172 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));

		// // Regra 173
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 173 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 
			// // Regra 180
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 180 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
		 
			// // Regra 182
		 resultadoEsperadoTeste = new QueryDataSet(connection);
		 resultadoEsperadoValidacao = new QueryDataSet(connection);
		 queries = carregarQuery(Origem.VALIDACAO_RENNER, "REGRA 182 (Ora).sql").split(";");
		 resultadoEsperadoValidacao.addTable("RESULTADO", queries[0]);
		 resultadoEsperadoTeste.addTable("RESULTADO", queries[1]);
		 Assertion.assertEquals(resultadoEsperadoValidacao.getTable("RESULTADO"),
		 resultadoEsperadoTeste.getTable("RESULTADO"));
	}

	public void limpaTeste() throws SQLException {

		final Statement statement = jdbcConnection.createStatement();
		String query = carregarQuery(CarregarQuery.Origem.TESTE,
				"limpa_teste.sql");

		for (int i = 0; i < query.trim().split(";").length; i++) {
			statement.addBatch(query.trim().split(";")[i]);
		}
		statement.executeBatch();
	}

}
