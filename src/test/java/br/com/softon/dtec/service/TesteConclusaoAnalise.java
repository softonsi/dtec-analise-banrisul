package br.com.softon.dtec.service;

import static br.com.softon.dtec.service.DtecLDService.ModoExecucao.TESTE;
import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.utils.CarregarQuery;
import br.com.softon.dtec.utils.CarregarQuery.Origem;
import softonPack.util.PropertieHandle;

public class TesteConclusaoAnalise {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TesteConclusaoAnalise.class);

	private Connection jdbcConnection;

	@Before
	public void setup() throws SQLException, DatabaseUnitException,
			ClassNotFoundException, IOException, InterruptedException {
		LOGGER.info("INIT SETUP");

		PropertieHandle.setTeste(true);

		final String driver = "oracle.jdbc.driver.OracleDriver";
		final String url = "jdbc:oracle:thin:@10.10.10.10:1521/orcldes";
		final String usr = "DTEC_LD_V3_PRD_BRB";
		final String pwd = "softon";
        
//        final String driver = "net.sourceforge.jtds.jdbc.Driver";
//        final String url = "jdbc:jtds:sqlserver://10.10.10.100:1433/dtec_ld_v3_prd_cartao_brb";
//        final String usr = "hugo";
//        final String pwd = "softon123";

        Configuracao.props_conn.setProperty("driver",driver);
        Configuracao.props_conn.setProperty("db_url",url);
        Configuracao.props_conn.setProperty("username",usr);
        Configuracao.props_conn.setProperty("password",pwd);
//        Configuracao.props_conn.setProperty("banco", "SQLSERVER");
        Configuracao.props_conn.setProperty("banco", "ORACLE");

		Class.forName(driver);

		jdbcConnection = DriverManager.getConnection(url, usr, pwd);

		final Statement statement = jdbcConnection.createStatement();
		String query = carregarQuery(CarregarQuery.Origem.TESTES_CONCLUSAO_ANALISE, "setup_inicial_teste_conclusao_analise.sql");

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
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(reader);
			String nextLine = "";
			StringBuffer sb = new StringBuffer();
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine);
			}

			final PreparedStatement ps = jdbcConnection.prepareStatement(query);
			ps.setString(1, sb.toString());

			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <h2>Teste de conclus??o da an??lise junto com a verifica????o de ponto de corte, flag de ld, flag de car??ncia,
	 * verifica????o de regra acumulativa, entre outros...</h2>
	 * <h3>Casos:</h3>
	 * <p>Cliente '36898457691' est?? presente no primeiro lote, por??m n??o tem disparo, ou seja, n??o foi enquadrado.</p>
	 * <p>Cliente '75598272091' est?? presente no primeiro lote, t??m disparo, mas sua pontua????o ficou abaixo do ponto de corte...
	 * Ent??o este ?? um cliente enquadrado em uma regra n??o acumulativa (regra 09) mas n??o apontado.</p>
	 * <p>Cliente '10259686115' est?? presente no primeiro lote, t??m disparo, mas sua pontua????o ficou abaixo do ponto de corte...
	 * Esse cliente esta enquadrado em uma regra acumulativa (regra 01) mas n??o apontado. </p>
	 * <p>Cliente '34296980149' est?? presente no primeiro lote, t??m disparo e foi enquadrado pela regra acumulativa (regra 01) que passou
	 * do ponto de corte e apontou como LD.</p>
	 * <p>Cliente '9142310000000' est?? presente no primeiro lote, t??m disparo e foi enquadrado pelas regras 04 e 09 que passou do 
	 * ponto de corte e apontou como LD.</p>
	 * <p>Cliente '4104551653' est?? presente no primeiro lote, t??m disparo e foi enquadrado pelas regras 01 e 04 que passou do ponto
	 * de corte e apontou como LD - detalhe: neste enquadramento, o cliente teve 2 disparos da regra 04 em transa????es diferentes,
	 * mas a pontua????o contou apenas um disparo, pois a regra 04 n??o ?? acumulativa.</p>
	 * <h3>Para o segundo lote:</h3>
	 * <p>Cliente '36898457691' est?? presente no segundo lote, por??m n??o tem disparo, ou seja, n??o foi enquadrado.</p>
	 * <p>Cliente '75598272091' est?? presente no segundo lote, t??m disparo, mas sua pontua????o ficou abaixo do ponto de corte...
	 * Ent??o este ?? um cliente enquadrado em uma regra n??o acumulativa (regra 09) mas n??o apontado.
	 * Por este motivo, existem 2 registros para este cliente na tabela de apontamentos - um apontamento para o primeiro lote e
	 * o outro apontamento para o segundo lote dos testes.</p>
	 * <p>Cliente '10259686115' est?? presente no segundo lote, t??m disparo, mas sua pontua????o ficou abaixo do ponto de corte...
	 * Esse cliente esta enquadrado em uma regra acumulativa (regra 01) mas n??o apontado. 
	 * Existem 2 registros para este cliente na tabela de apontamentos - um apontamento para o primeiro lote e o outro apontamento
	 * para o segundo lote dos testes.  Como ele n??o foi apontado no lote anterior, n??o somou-se a regra 01 (acumulativa) para ele.</p>
	 * <p>Cliente '34296980149' est?? presente no segundo lote, t??m disparo do lote anterior onde foi apontado como LD e foi enquadrado 
	 * novamente pela regra acumulativa (regra 01).</p>
	 * <p>Cliente '9142310000000' est?? presente no segundo lote, t??m disparo do lote anterior onde foi apontado como LD.  Neste segundo lote  
	 * ele foi enquadrado pelas regras 04 e 09 novamente, por??m, como as regras n??o s??o acumulativas, n??o somaram pontua????o, deixando 
	 * a pontua????o igual ao apontamento do lote anterior.</p>
	 * <p>Cliente '4104551653' est?? presente no segundo lote, t??m disparo do lote anterior onde foi apontado como LD e foi enquadrado novamente 
	 * pelas regras 01 e 04 - detalhe: neste enquadramento, o cliente teve mais 2 disparos da regra 04 em transa????es diferentes,
	 * mas a pontua????o contou apenas um disparo do lote anterior, pois a regra 04 n??o ?? acumulativa.  J?? a regra 01 teve mais 2 disparos
	 * e elevou a pontua????o pois ?? uma regra acumulativa.</p>
	 * <h3>Para o terceiro lote:</h3>
	 * <p>Cliente '10426500000000' movido ao Hist??rico como N??o Tratado (status 30-Movido pelo Sistema).  Este cliente ?? enquadrado
	 * novamente pelas mesmas regras que o fizeram apontar nos lotes anteriores (regras 01 e 04), por??m foi apontado novamente pois
	 * quando o cliente ?? movido pelo Sistema, ele n??o se enquadra na regra de car??ncia.</p>
	 * <p>Cliente '34296980149' tratado como ???N??o LD??? (status 20-Cliente Liberado pelo Analista) e ainda n??o est?? no hist??rico...
	 * O cliente ?? enquadrado novamente pela mesma regra j?? disparada anteriormente (regra 01), deveria ser apontado pois passou
	 * do ponto de corte, por??m, devido ?? regra de car??ncia, tendo ele o mesmo disparo j?? tratado dentro do per??odo de car??ncia,
	 * n??o ?? apontado novamente e fica com o flag de car??ncia igual a zero.</p>
	 * <p>Cliente '4104551653' tratado como ???N??o LD??? (status 20-Cliente Liberado pelo Analista) e foi movido ao hist??rico...
	 * Esse cliente ?? enquadrado novamente pelas mesmas regras j?? disparadas anteriormente (regras 01 e 04), deveria ser apontado pois passou
	 * do ponto de corte, por??m, devido ?? regra de car??ncia, tendo ele o mesmo disparo j?? tratado dentro do per??odo de car??ncia,
	 * n??o ?? apontado novamente e fica com o flag de car??ncia igual a zero.</p>
	 * <p>Cliente '3366979100' tratado como  ???LD??? (status 90-Informado ao Coaf) e ainda n??o est?? no hist??rico...
	 * Esse cliente ?? enquadrado novamente pelas mesmas regras j?? disparadas anteriormente (regras 01 e 04), deveria ser apontado pois passou
	 * do ponto de corte, por??m, devido ?? regra de car??ncia, tendo ele o mesmo disparo j?? tratado dentro do per??odo de car??ncia,
	 * n??o ?? apontado novamente e fica com o flag de car??ncia igual a zero.</p>
	 * <p>Cliente '10193030500' tratado como ???LD??? (status 90-Informado ao Coaf) e foi movido ao hist??rico...
	 * Esse cliente ?? enquadrado novamente pelas mesmas regras j?? disparadas anteriormente (regras 01 e 04), deveria ser apontado pois passou
	 * do ponto de corte, por??m, devido ?? regra de car??ncia, tendo ele o mesmo disparo j?? tratado dentro do per??odo de car??ncia,
	 * n??o ?? apontado novamente e fica com o flag de car??ncia igual a zero.</p>
	 * <p>Cliente '9142310000000' tratado como ???N??o LD??? (status 20-Cliente Liberado pelo Analista) e ainda n??o est?? no hist??rico...
	 * Esse cliente ?? enquadrado pelas regras 04 e 09 da mesma forma que os apontamentos anteriores, por??m agora houve enquadramento 
	 * tamb??m da regra 01, o que fez com que este apontamento ficasse diferente dos apontamentos anteriores.  Por isso, este cliente 
	 * foi apontado novamente.</p>
	 * <p>Cliente '8576140000000' tratado como ???Em An??lise??? (status 5-Em An??lise)...
	 * Esse cliente j?? havia sido enquadrado pelas regras 01, 04 e 09 e apontado.  Por isso come??ou a ser tratado pelo analista.
	 * Houve agora novos enquadramentos para este cliente pelas mesmas regras j?? apontadas.   
	 * Neste caso, os enquadramentos foram agregados e a pontua????o somada de acordo com as regras apontadas, como segue:</p>
	 * <ul><li>Regra 01 - J?? havia sido disparada, por??m ?? acumulativa, portanto somou 2 ao total;</li>
	 * <li>Regra 04 - J?? havia sido disparada, mas n??o ?? acumulativa, ent??o n??o somou nenhum ponto ao total;</li>
	 * <li>Regra 09 - J?? havia sido disparada, mas n??o ?? acumulativa, ent??o n??o somou nenhum ponto ao total.</li></ul>
	 * <p>Antes da execu????o deste terceiro lote, a pontua????o deste cliente era de 9.  Ap??s a execu????o deste terceiro lote, sua pontua????o 
	 * subiu para 11 pontos.</p>
	 * <p><b>Todos os casos supracitados possuem valida????es por queries na tabela de apontamentos.</b></p>
	 * @throws DatabaseUnitException
	 * @throws MalformedURLException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testeConclusaoAnalise()
			throws DatabaseUnitException, MalformedURLException, SQLException,
			InterruptedException {

		LOGGER.info("-----------------------------------------------------------------------------");
		LOGGER.info("-------------TESTE COM LOTE 2015090101 (PRIMEIRO LOTE DOS TESTES)------------");
		LOGGER.info("-----------------------------------------------------------------------------");

		updateRegra(
				"regras-brb-cartoes/REGRA 01.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra01.sql"));
		updateRegra(
				"regras-brb-cartoes/REGRA 04.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra04.sql"));
		updateRegra(
				"regras-brb-cartoes/REGRA 09.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra09.sql"));

		final IDatabaseConnection connection = new DatabaseConnection(
				jdbcConnection);
		final DtecLDService service = new DtecLDService();
		service.execute(TESTE);

		{
			executaAsserts(connection);
		}

		LOGGER.info("-----------------------------------------------------------------------------");
		LOGGER.info("-------------TESTE COM LOTE 2015090201 (SEGUNDO LOTE DOS TESTES)-------------");
		LOGGER.info("-----------------------------------------------------------------------------");

		// Novo lote para o segundo processamento - objetivo principal de verificar apontamentos com os mesmos clientes
		// apontados ou n??o no lote processado anteriormente...
		final Statement statement = jdbcConnection.createStatement();
		String query = carregarQuery(CarregarQuery.Origem.TESTES_CONCLUSAO_ANALISE, "setup_secundario_teste_conclusao_analise.sql");

		for (int i = 0; i < query.trim().split(";").length; i++) {
			statement.addBatch(query.trim().split(";")[i]);
		}
		statement.executeBatch();

		// Preparando as mesmas regras usadas no lote anterior para disparar neste pr??ximo lote...
		updateRegra(
				"regras-brb-cartoes/REGRA 01.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra01.sql"));
		updateRegra(
				"regras-brb-cartoes/REGRA 04.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra04.sql"));
		updateRegra(
				"regras-brb-cartoes/REGRA 09.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra09.sql"));
		
		// Executando o m??dulo de an??lise pela segunda vez para consumir o pr??ximo lote criado para testes...
		service.execute(TESTE);

		{
			executaAssertsSegundoLote(connection);
		}

		LOGGER.info("-----------------------------------------------------------------------------");
		LOGGER.info("------------TESTE COM LOTE 2015100101 (TERCEIRO LOTE DOS TESTES)-------------");
		LOGGER.info("-----------------------------------------------------------------------------");

		// Novo lote para o terceiro processamento - objetivo principal de verificar car??ncia nos apontamentos...
		final Statement statement2 = jdbcConnection.createStatement();
		String query2 = carregarQuery(CarregarQuery.Origem.TESTES_CONCLUSAO_ANALISE, "setup_terciario_teste_conclusao_analise.sql");

		for (int i = 0; i < query2.trim().split(";").length; i++) {
			statement2.addBatch(query2.trim().split(";")[i]);
		}
		statement2.executeBatch();

		// Preparando as mesmas regras usadas no lote anterior para disparar neste pr??ximo lote...
		updateRegra(
				"regras-brb-cartoes/REGRA 01.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra01.sql"));
		updateRegra(
				"regras-brb-cartoes/REGRA 04.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra04.sql"));
		updateRegra(
				"regras-brb-cartoes/REGRA 09.drl",
				carregarQuery(CarregarQuery.Origem.TESTE,
						"regra09.sql"));
		
		// Executando o m??dulo de an??lise pela segunda vez para consumir o pr??ximo lote criado para testes...
		service.execute(TESTE);

		{
			executaAssertsCarencia(connection);
		}

		jdbcConnection.close();
	}

	private void executaAsserts(final IDatabaseConnection connection)
			throws AmbiguousTableNameException, DatabaseUnitException,
			DataSetException, MalformedURLException {

		// Cliente 36898457691
		final IDataSet resultado = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-36898457691.xml"));
		final QueryDataSet resultadoDB = new QueryDataSet(connection);
		resultadoDB
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-36898457691.sql"));

		Assertion.assertEquals(resultado.getTable("RESULTADO"),
				resultadoDB.getTable("RESULTADO"));

		// Cliente 75598272091
		final IDataSet resultado2 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-75598272091.xml"));
		final QueryDataSet resultadoDB2 = new QueryDataSet(connection);
		resultadoDB2
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-75598272091.sql"));

		Assertion.assertEquals(resultado2.getTable("RESULTADO"),
				resultadoDB2.getTable("RESULTADO"));

		// Cliente 10259686115
		final IDataSet resultado3 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-10259686115.xml"));
		final QueryDataSet resultadoDB3 = new QueryDataSet(connection);
		resultadoDB3
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-10259686115.sql"));

		Assertion.assertEquals(resultado3.getTable("RESULTADO"),
				resultadoDB3.getTable("RESULTADO"));

		// Cliente 34296980149
		final IDataSet resultado4 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-34296980149.xml"));
		final QueryDataSet resultadoDB4 = new QueryDataSet(connection);
		resultadoDB4
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-34296980149.sql"));

		Assertion.assertEquals(resultado4.getTable("RESULTADO"),
				resultadoDB4.getTable("RESULTADO"));

		// Cliente 9142310000000
		final IDataSet resultado5 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-9142310000000.xml"));
		final QueryDataSet resultadoDB5 = new QueryDataSet(connection);
		resultadoDB5
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-9142310000000.sql"));

		Assertion.assertEquals(resultado5.getTable("RESULTADO"),
				resultadoDB5.getTable("RESULTADO"));

		// Cliente 4104551653
		final IDataSet resultado6 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-4104551653.xml"));
		final QueryDataSet resultadoDB6 = new QueryDataSet(connection);
		resultadoDB6
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-4104551653.sql"));

		Assertion.assertEquals(resultado6.getTable("RESULTADO"),
				resultadoDB6.getTable("RESULTADO"));
	}

	private void executaAssertsSegundoLote(final IDatabaseConnection connection)
			throws AmbiguousTableNameException, DatabaseUnitException,
			DataSetException, MalformedURLException {

		// Cliente 36898457691
		final IDataSet resultado = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-36898457691.xml"));
		final QueryDataSet resultadoDB = new QueryDataSet(connection);
		resultadoDB
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-36898457691.sql"));

		Assertion.assertEquals(resultado.getTable("RESULTADO"),
				resultadoDB.getTable("RESULTADO"));
		
		// Cliente 75598272091
		final IDataSet resultado2 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-75598272091-segundo-lote.xml"));
		final QueryDataSet resultadoDB2 = new QueryDataSet(connection);
		resultadoDB2
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-75598272091-segundo-lote.sql"));

		Assertion.assertEquals(resultado2.getTable("RESULTADO"),
				resultadoDB2.getTable("RESULTADO"));

		// Cliente 10259686115
		final IDataSet resultado3 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-10259686115-segundo-lote.xml"));
		final QueryDataSet resultadoDB3 = new QueryDataSet(connection);
		resultadoDB3
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-10259686115-segundo-lote.sql"));

		Assertion.assertEquals(resultado3.getTable("RESULTADO"),
				resultadoDB3.getTable("RESULTADO"));

		// Cliente 34296980149
		final IDataSet resultado4 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-34296980149-segundo-lote.xml"));
		final QueryDataSet resultadoDB4 = new QueryDataSet(connection);
		resultadoDB4
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-34296980149-segundo-lote.sql"));

		Assertion.assertEquals(resultado4.getTable("RESULTADO"),
				resultadoDB4.getTable("RESULTADO"));

		// Cliente 9142310000000
		final IDataSet resultado5 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-9142310000000-segundo-lote.xml"));
		final QueryDataSet resultadoDB5 = new QueryDataSet(connection);
		resultadoDB5
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-9142310000000-segundo-lote.sql"));

		Assertion.assertEquals(resultado5.getTable("RESULTADO"),
				resultadoDB5.getTable("RESULTADO"));

		// Cliente 4104551653
		final IDataSet resultado6 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-4104551653-segundo-lote.xml"));
		final QueryDataSet resultadoDB6 = new QueryDataSet(connection);
		resultadoDB6
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-4104551653-segundo-lote.sql"));

		Assertion.assertEquals(resultado6.getTable("RESULTADO"),
				resultadoDB6.getTable("RESULTADO"));
	}

	private void executaAssertsCarencia(final IDatabaseConnection connection)
			throws AmbiguousTableNameException, DatabaseUnitException,
			DataSetException, MalformedURLException {

		// Cliente 10426500000000
		final IDataSet resultado = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-10426500000000.xml"));
		final QueryDataSet resultadoDB = new QueryDataSet(connection);
		resultadoDB
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-10426500000000.sql"));

		Assertion.assertEquals(resultado.getTable("RESULTADO"),
				resultadoDB.getTable("RESULTADO"));
		
		// Cliente 10193030500
		final IDataSet resultado2 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-10193030500.xml"));
		final QueryDataSet resultadoDB2 = new QueryDataSet(connection);
		resultadoDB2
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-10193030500.sql"));

		Assertion.assertEquals(resultado2.getTable("RESULTADO"),
				resultadoDB2.getTable("RESULTADO"));

		// Cliente 9142310000000
		final IDataSet resultado3 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-9142310000000-terceiro-lote.xml"));
		final QueryDataSet resultadoDB3 = new QueryDataSet(connection);
		resultadoDB3
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-9142310000000-terceiro-lote.sql"));

		Assertion.assertEquals(resultado3.getTable("RESULTADO"),
				resultadoDB3.getTable("RESULTADO"));

		// Cliente 34296980149
		final IDataSet resultado4 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-34296980149-terceiro-lote.xml"));
		final QueryDataSet resultadoDB4 = new QueryDataSet(connection);
		resultadoDB4
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-34296980149-terceiro-lote.sql"));

		Assertion.assertEquals(resultado4.getTable("RESULTADO"),
				resultadoDB4.getTable("RESULTADO"));

		// Cliente 3366979100
		final IDataSet resultado5 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-3366979100.xml"));
		final QueryDataSet resultadoDB5 = new QueryDataSet(connection);
		resultadoDB5
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-3366979100.sql"));

		Assertion.assertEquals(resultado5.getTable("RESULTADO"),
				resultadoDB5.getTable("RESULTADO"));

		// Cliente 4104551653
		final IDataSet resultado6 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-4104551653-terceiro-lote.xml"));
		final QueryDataSet resultadoDB6 = new QueryDataSet(connection);
		resultadoDB6
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-4104551653-terceiro-lote.sql"));

		Assertion.assertEquals(resultado6.getTable("RESULTADO"),
				resultadoDB6.getTable("RESULTADO"));

		// Cliente 8576140000000
		final IDataSet resultado7 = new FlatXmlDataSetBuilder()
				.build(new File(
						"data/cliente-8576140000000.xml"));
		final QueryDataSet resultadoDB7 = new QueryDataSet(connection);
		resultadoDB7
				.addTable(
						"RESULTADO",
						carregarQuery(Origem.TESTES_CONCLUSAO_ANALISE,
								"cliente-8576140000000.sql"));

		Assertion.assertEquals(resultado7.getTable("RESULTADO"),
				resultadoDB7.getTable("RESULTADO"));
	}
}
