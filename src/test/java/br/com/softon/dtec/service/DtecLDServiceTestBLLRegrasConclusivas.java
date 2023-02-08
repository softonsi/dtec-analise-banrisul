package br.com.softon.dtec.service;

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
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.dao.ObjetoAnaliseDAO;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.ParametrosRegras;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.service.DtecLDService.ModoExecucao;
import br.com.softon.dtec.utils.CarregarQuery;
import softonPack.util.PropertieHandle;

public class DtecLDServiceTestBLLRegrasConclusivas {

	private final static Logger LOGGER = LoggerFactory.getLogger(DtecLDServiceTestBLLRegrasConclusivas.class);

	private Connection jdbcConnection;
	private Queue<String> lotes;

	private BufferedReader br;
	private BufferedWriter bw;
	private StringBuilder bupdates;

	@Before
	public void setup()
			throws SQLException, DatabaseUnitException, ClassNotFoundException, IOException, InterruptedException {
		LOGGER.info("INIT SETUP");

		PropertieHandle.setTeste(true);

		final String driver = "oracle.jdbc.driver.OracleDriver";
		final String url = "jdbc:oracle:thin:@10.10.10.21:1521/orcl";
		final String usr = "DTEC_BANRISUL_APONT";
		final String pwd = "softon";

		Configuracao.props_conn.setProperty("driver", driver);
		Configuracao.props_conn.setProperty("db_url", url);
		Configuracao.props_conn.setProperty("username", usr);
		Configuracao.props_conn.setProperty("password", pwd);
		Configuracao.props_conn.setProperty("banco", "ORACLE");

		Configuracao.props_conf.setProperty("gravarLogPerformance", "false");
		Configuracao.props_conf.setProperty("desligar_job_kettle", "true");
		
		Configuracao.props_conf.setProperty("desligar_transp_hist", "true");
		
		Configuracao.props_conf.setProperty("desligar_criacao_lote_mensal", "false");

		Configuracao.props_conf.setProperty("qtd_rows_per_page", "2000");

		Configuracao.props_conf.setProperty("wait_next_lote_seconds", "10000");

		Class.forName(driver);

		jdbcConnection = DriverManager.getConnection(url, usr, pwd);
		lotes = new LinkedList<String>();

		final Statement statement = jdbcConnection.createStatement();
		statement.addBatch("update tb_regra set fl_regra_ativa = 0");
		
		execScript("inicio_teste_rc.sql", null);

		try {
			statement.executeBatch();
		} catch (Exception e) {
			LOGGER.warn("Ignorando erros..." + e);
			e.printStackTrace();
			System.exit(0);
		}

		bupdates = new StringBuilder();

		Writer writer = new FileWriter("regras-bll-diaria/update_bll.sql");
		bw = new BufferedWriter(writer);
		bw.write("set define off; \n");
		bw.write("DECLARE \n");
		bupdates.append("\n BEGIN");
		
		//  Apagar os arquivos sql gerados no processamento anterior com parametros...
		deleteTree(new File("regras-bll-diaria/regras-com-params"));

		updateRegra("regras-bll-mensal/REGRA 000.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra000.sql"));
		updateRegra("regras-bll-mensal/REGRA 10001.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra10001.sql"));
		
		bupdates.append("\n end;");
		bw.write(bupdates.toString());

		bw.flush();
		bw.close();

		LOGGER.info("END SETUP");
	}

	/**
	 * Ativa e atualiza regras para execução do teste
	 * 
	 * @param regra - Caminho completo para a regra
	 * @param query - query de atualização/ativação da regra
	 * @throws IOException
	 * @throws Exception
	 */
	protected void updateRegra(String regra, String query) throws SQLException, IOException {
		try {
			File file = new File(regra);
			Reader reader = new FileReader(file);
			br = new BufferedReader(reader);
			String nextLine = "";
			StringBuffer sb = new StringBuffer();
			StringBuffer sbls = new StringBuffer();
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine);// .append(System.lineSeparator());
				sbls.append(nextLine).append(System.lineSeparator());
			}

			final PreparedStatement ps = jdbcConnection.prepareStatement(query);
			ps.setString(1, sb.toString());

			ps.execute();
			
			createSQLRegraWithParams(query.substring(query.length() - 3).trim(), sbls);

			String banco = Configuracao.props_conn.getProperty("banco");

			if ("ORACLE".equalsIgnoreCase(banco)) {

				String replaceField = regra.split("/")[1].replace(".", "_").replace(" ", "_");

				query = regra.split("/")[1].replace(".", "").replace(" ", "") + " Clob := " + replaceField + ";";
				// query = "update tb_regra set tx_regra_dinamica = " + replaceField
				// + " where " + query.substring(query.length() - 15);

				String cd_regra = query.substring(query.length() - 15).split("_")[1];

				bupdates.append(
						"\n update tb_regra set dt_regra_dinamica = sysdate, ds_erro_regra = null, tx_regra_dinamica = "
								+ regra.split("/")[1].replace(".", "").replace(" ", "") + " where CD_VERSAO_SISTEMA = 3 and cd_regra = "
								+ cd_regra + ";");

				String str = sb.toString().replace("'", "''");
				String result = "";

				if (str.length() >= 2400) {
					while (str.length() >= 2400) {

						int lastBlank = str.substring(0, 2400).lastIndexOf(" ");

						result += str.substring(0, lastBlank) + " '\n||'";
						str = str.substring(lastBlank);
					}
				}
				result += str;

				bw.append("\n" + query.replace(replaceField, "'" + result + "'"));
			} else {
				query = "update tb_regra set dt_regra = getdate(), ds_erro_regra = null, tx_regra_dinamica = ? where CD_VERSAO_SISTEMA = 3 and "
						+ query.substring(query.length() - 15);

				String str = sb.toString().replace(System.lineSeparator(), "");

				bw.append(query.replace("?", "'" + str + "'") + ";\n");
			}

		} catch (SQLException e) {
			throw e;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param regra
	 * @param sb
	 * @throws Exception
	 */
	private void createSQLRegraWithParams(String regra, StringBuffer sb) throws Exception {
		String sqlRegra = sb.toString();
		
		if(regra.contains("=")) regra = regra.substring(regra.indexOf("=") + 1).trim();
		
		final Collection<ParametrosRegras> listaParametrosRegras = ObjetoAnaliseDAO.getParams(new Regra(Short.valueOf(regra), (byte) 3), new ControleSimulacao(), false);
		
		sqlRegra = sqlRegra.replaceAll(":cd_sublote", "1");
		
		if (!listaParametrosRegras.isEmpty()) {
			for (ParametrosRegras param : listaParametrosRegras) {
				sqlRegra = sqlRegra.replaceAll(":" + param.getNomeCampo(), param.getValorParametro()+"");
				sqlRegra = sqlRegra.replaceAll(":DS-" + param.getNomeCampo(), param.getDescricaoCampo());
				sqlRegra = sqlRegra.replaceAll(":LS-" + param.getNomeCampo(), param.getDescricaoParametro());
				sqlRegra = sqlRegra.replaceAll(":NM-" + param.getNomeCampo(), param.getNomeCampo()+"");
			} 
		}

		Writer w = new FileWriter("regras-bll-diaria/regras-com-params/regra-"+ regra +".sql");
		BufferedWriter bwriter = new BufferedWriter(w);
		bwriter.write(sqlRegra);

		bwriter.flush();
		bwriter.close();
	}  
    
    public static void deleteTree(File inFile) {  
		if (inFile.isFile()) {  
			inFile.delete();  
		} else {  
			File files[] = inFile.listFiles();  
			for (int i=0;i< files.length; i ++) {  
			deleteTree(files[i]);  
			}
		}  
	}

	@Test
	public void testeBLL() throws DatabaseUnitException, MalformedURLException, SQLException, InterruptedException {

		final DtecLDService service = new DtecLDService();

		execScript("atualiza_teste_rc.sql", "201901");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201902");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201903");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201904");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201905");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201906");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201907");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201908");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201909");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201910");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201911");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "201912");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202001");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202002");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202003");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202004");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202005");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202006");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202007");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202008");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202010");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202011");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202012");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202101");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202102");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202103");		

		service.execute(ModoExecucao.TESTE);

		execScript("atualiza_teste_rc.sql", "202104");		

		service.execute(ModoExecucao.TESTE);
		
		if (!jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public void execScript(String script, String lote) throws SQLException {

		final Statement statement = jdbcConnection.createStatement();

		String query = carregarQuery(CarregarQuery.Origem.TESTE, script);

		for (int i = 0; i < query.trim().split(";").length; i++) {
			statement.addBatch(query.split(";")[i].replaceAll(":cd_lote", lote));
		}
		try {
			statement.executeBatch();
		} catch (Exception e) {
			LOGGER.warn("Erro ao executar o script "+script+", ignorando..." + e);
			e.printStackTrace();
		}
	}

}
