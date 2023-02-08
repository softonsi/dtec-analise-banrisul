package br.com.softon.dtec.service;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;
import br.com.softon.dtec.dao.ObjetoAnaliseDAO;
import br.com.softon.dtec.entity.ControleSimulacao;
import br.com.softon.dtec.entity.ParametrosRegras;
import br.com.softon.dtec.entity.Regra;
import br.com.softon.dtec.service.DtecLDService.ModoExecucao;
import br.com.softon.dtec.utils.CarregarQuery;
import br.com.softon.dtec.utils.KettleJobRunner;
import br.com.softon.util.RunWebDriver;
import br.com.softon.util.WebDriverManager;
import softonPack.util.PropertieHandle;

public class DtecLDServiceTestBLLCasos {

	private final static Logger LOGGER = LoggerFactory.getLogger(DtecLDServiceTestBLLCasos.class);

	private Connection jdbcConnection;

	private BufferedReader br;
	private BufferedWriter bw;
	private StringBuilder bupdates;
	
	private String urlServer;
	private static long TEMPO_ESPERA_COMANDOS_TELA = 800;

	@Before
	public void setup()
			throws SQLException, DatabaseUnitException, ClassNotFoundException, IOException, InterruptedException {
		LOGGER.info("INIT SETUP");
		
		WebDriverManager.initWebDriver();

//		urlServer = "http://10.10.10.32:8053/dtec-front-banrisul/index-teste-banrisul.html";
		urlServer = "http://localhost:8080/dtec-front-banrisul/index-teste-banrisul.html";

		PropertieHandle.setTeste(true);

		final String driver = "oracle.jdbc.driver.OracleDriver";
		final String url = "jdbc:oracle:thin:@10.10.10.21:1521/orcl";
		final String usr = "DTEC_BANRISUL_PRD_SEM_AG";
		final String pwd = "softon";

		Configuracao.props_conn.setProperty("driver", driver);
		Configuracao.props_conn.setProperty("db_url", url);
		Configuracao.props_conn.setProperty("username", usr);
		Configuracao.props_conn.setProperty("password", pwd);
		Configuracao.props_conn.setProperty("banco", "ORACLE");

		Configuracao.props_conf.setProperty("gravarLogPerformance", "false");
		Configuracao.props_conf.setProperty("desligar_job_kettle", "true");

		Configuracao.props_conf.setProperty("kettle_file_ponto_corte", "/home/softon/JAVA/workspace_dtec_ld_v3_analise/dtec-ld-v3-analise-banrisul/manutencao/INICIO_MANU_BI.kjb");		// usado somente nos testes
		
		Configuracao.props_conf.setProperty("desligar_regras_conclusivas", "false");
		
		Configuracao.props_conf.setProperty("desligar_criacao_lote_mensal", "true");

		Configuracao.props_conf.setProperty("qtd_rows_per_page", "2000");

		Configuracao.props_conf.setProperty("wait_next_lote_seconds", "10000");

		Class.forName(driver);

		jdbcConnection = DriverManager.getConnection(url, usr, pwd);

		final Statement statement = jdbcConnection.createStatement();
		statement.addBatch("update tb_regra set fl_regra_ativa = 0");

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

		updateRegra("regras-bll-diaria/REGRA 020.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra020.sql"));
//		updateRegra("regras-bll-diaria/REGRA 022.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra022.sql"));
//		updateRegra("regras-bll-diaria/REGRA 024.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra024.sql"));
//		updateRegra("regras-bll-diaria/REGRA 025.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra025.sql"));
//		updateRegra("regras-bll-diaria/REGRA 026.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra026.sql"));
//		updateRegra("regras-bll-diaria/REGRA 027.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra027.sql"));
//		updateRegra("regras-bll-diaria/REGRA 030.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra030.sql"));
//		updateRegra("regras-bll-diaria/REGRA 031.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra031.sql"));
//		updateRegra("regras-bll-diaria/REGRA 032.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra032.sql"));
//		updateRegra("regras-bll-diaria/REGRA 033.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra033.sql"));
//		updateRegra("regras-bll-diaria/REGRA 038.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra038.sql"));
//		updateRegra("regras-bll-diaria/REGRA 043.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra043.sql"));
		updateRegra("regras-bll-diaria/REGRA 051.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra051.sql"));
//		updateRegra("regras-bll-diaria/REGRA 052.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra052.sql"));
//		updateRegra("regras-bll-diaria/REGRA 060.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra060.sql"));
//		updateRegra("regras-bll-diaria/REGRA 065.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra065.sql"));
//		updateRegra("regras-bll-diaria/REGRA 070.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra070.sql"));
//		updateRegra("regras-bll-diaria/REGRA 071.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra071.sql"));
//		updateRegra("regras-bll-diaria/REGRA 073.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra073.sql"));
//		updateRegra("regras-bll-diaria/REGRA 074.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra074.sql"));
//		updateRegra("regras-bll-diaria/REGRA 075.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra075.sql"));
//		updateRegra("regras-bll-diaria/REGRA 076.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra076.sql"));
//		updateRegra("regras-bll-diaria/REGRA 080.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra080.sql"));
//		updateRegra("regras-bll-diaria/REGRA 081.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra081.sql"));
//		updateRegra("regras-bll-diaria/REGRA 082.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra082.sql"));
//		updateRegra("regras-bll-diaria/REGRA 083.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra083.sql"));
//		updateRegra("regras-bll-diaria/REGRA 084.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra084.sql"));
//		updateRegra("regras-bll-diaria/REGRA 085.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra085.sql"));
//		updateRegra("regras-bll-diaria/REGRA 086.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra086.sql"));
//		updateRegra("regras-bll-diaria/REGRA 087.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra087.sql"));
//		updateRegra("regras-bll-diaria/REGRA 088.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra088.sql"));
//		updateRegra("regras-bll-diaria/REGRA 089.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra089.sql"));
//		updateRegra("regras-bll-diaria/REGRA 090.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra090.sql"));
//		updateRegra("regras-bll-diaria/REGRA 091.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra091.sql"));
//		updateRegra("regras-bll-diaria/REGRA 092.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra092.sql"));
//		updateRegra("regras-bll-diaria/REGRA 094.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra094.sql"));
//		updateRegra("regras-bll-diaria/REGRA 120.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra120.sql"));
//		updateRegra("regras-bll-diaria/REGRA 124.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra124.sql"));
//		updateRegra("regras-bll-diaria/REGRA 125.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra125.sql"));
//		updateRegra("regras-bll-diaria/REGRA 126.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra126.sql"));
//		updateRegra("regras-bll-diaria/REGRA 210.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra210.sql"));
//		updateRegra("regras-bll-diaria/REGRA 402.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra402.sql"));
//		updateRegra("regras-bll-diaria/REGRA 403.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra403.sql"));
//		updateRegra("regras-bll-diaria/REGRA 405.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra405.sql"));
//		
		updateRegra("regras-bll-mensal/REGRA 000.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra000.sql"));
//		updateRegra("regras-bll-mensal/REGRA 012.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra012.sql"));
//		updateRegra("regras-bll-mensal/REGRA 021.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra021.sql"));
//		updateRegra("regras-bll-mensal/REGRA 023.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra023.sql"));
//		updateRegra("regras-bll-mensal/REGRA 041.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra041.sql"));
//		updateRegra("regras-bll-mensal/REGRA 044.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra044.sql"));
//		updateRegra("regras-bll-mensal/REGRA 045.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra045.sql"));
//		updateRegra("regras-bll-mensal/REGRA 046.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra046.sql"));
//		updateRegra("regras-bll-mensal/REGRA 047.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra047.sql"));
//		updateRegra("regras-bll-mensal/REGRA 048.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra048.sql"));
//		updateRegra("regras-bll-mensal/REGRA 050.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra050.sql"));
//		updateRegra("regras-bll-mensal/REGRA 053.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra053.sql"));
//		updateRegra("regras-bll-mensal/REGRA 054.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra054.sql"));
//		updateRegra("regras-bll-mensal/REGRA 055.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra055.sql"));
//		updateRegra("regras-bll-mensal/REGRA 056.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra056.sql"));
//		updateRegra("regras-bll-mensal/REGRA 057.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra057.sql"));
//		updateRegra("regras-bll-mensal/REGRA 058.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra058.sql"));
//		updateRegra("regras-bll-mensal/REGRA 061.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra061.sql"));
//		updateRegra("regras-bll-mensal/REGRA 063.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra063.sql"));
//		updateRegra("regras-bll-mensal/REGRA 067.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra067.sql"));
//		updateRegra("regras-bll-mensal/REGRA 068.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra068.sql"));
//		updateRegra("regras-bll-mensal/REGRA 069.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra069.sql"));
//		updateRegra("regras-bll-mensal/REGRA 093.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra093.sql"));
//		updateRegra("regras-bll-mensal/REGRA 101.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra101.sql"));
//		updateRegra("regras-bll-mensal/REGRA 102.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra102.sql"));
//		updateRegra("regras-bll-mensal/REGRA 103.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra103.sql"));
//		updateRegra("regras-bll-mensal/REGRA 104.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra104.sql"));
//		updateRegra("regras-bll-mensal/REGRA 110.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra110.sql"));
//		updateRegra("regras-bll-mensal/REGRA 111.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra111.sql"));
//		updateRegra("regras-bll-mensal/REGRA 112.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra112.sql"));
//		updateRegra("regras-bll-mensal/REGRA 160.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra160.sql"));
//		updateRegra("regras-bll-mensal/REGRA 161.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra161.sql"));
//		updateRegra("regras-bll-mensal/REGRA 400.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra400.sql"));
//		updateRegra("regras-bll-mensal/REGRA 401.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra401.sql"));
//		updateRegra("regras-bll-mensal/REGRA 404.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra404.sql"));
//		updateRegra("regras-bll-mensal/REGRA 049.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra049.sql"));
//		updateRegra("regras-bll-mensal/REGRA 042.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra042.sql"));
//		updateRegra("regras-bll-mensal/REGRA 040.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra040.sql"));
//		updateRegra("regras-bll-mensal/REGRA 034.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra034.sql"));
//		updateRegra("regras-bll-mensal/REGRA 035.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra035.sql"));
//		updateRegra("regras-bll-mensal/REGRA 036.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra036.sql"));
//		updateRegra("regras-bll-mensal/REGRA 037.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra037.sql"));
//		updateRegra("regras-bll-mensal/REGRA 204.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra204.sql"));
//		updateRegra("regras-bll-mensal/REGRA 205.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra205.sql"));
//		updateRegra("regras-bll-mensal/REGRA 206.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra206.sql"));
//		updateRegra("regras-bll-mensal/REGRA 207.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra207.sql"));
//		updateRegra("regras-bll-mensal/REGRA 208.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra208.sql"));
//		updateRegra("regras-bll-mensal/REGRA 209.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra209.sql"));
//		updateRegra("regras-bll-mensal/REGRA 211.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra211.sql"));
//		updateRegra("regras-bll-mensal/REGRA 212.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra212.sql"));
//		updateRegra("regras-bll-mensal/REGRA 301.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra301.sql"));
//		updateRegra("regras-bll-mensal/REGRA 302.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra302.sql"));
//		updateRegra("regras-bll-mensal/REGRA 303.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra303.sql"));
//		updateRegra("regras-bll-mensal/REGRA 304.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra304.sql"));
//		updateRegra("regras-bll-mensal/REGRA 305.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra305.sql"));
//		updateRegra("regras-bll-mensal/REGRA 310.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra310.sql"));
//		updateRegra("regras-bll-mensal/REGRA 311.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra311.sql"));
//		updateRegra("regras-bll-mensal/REGRA 312.drl", carregarQuery(CarregarQuery.Origem.TESTE, "regra312.sql"));
		
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
	public void testeBLL() throws Exception {        

		final DtecLDService service = new DtecLDService();

//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 1***********************\n\n");
//		cenarioUm(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 2***********************\n\n");
//		cenarioDois(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 3***********************\n\n");
//		cenarioTres(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 4***********************\n\n");
//		cenarioQuatro(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 5***********************\n\n");
//		cenarioCinco(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 6***********************\n\n");
//		cenarioSeis(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 7***********************\n\n");
//		cenarioSete(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 8***********************\n\n");
//		cenarioOito(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 9***********************\n\n");
//		cenarioNove(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 10***********************\n\n");
//		cenarioDez(service);
//
//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 11***********************\n\n");
//		cenarioOnze(service);

		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 12***********************\n\n");
		cenarioDoze(service);

//		LOGGER.info("\n\n***********************EXECUTANDO CENÁRIO 13***********************\n\n");
//		cenarioTreze(service);

		if (!jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
		LOGGER.info("Testes executados com sucesso!");
	}
	
	/**
	 * Cenário de teste 1

	 * Cliente Karina Santos - 24089207010 *dados*
	
	 * Descrição do cenário.

	 * - Cliente disparado pela regra 20 e 51 no dia 25/06/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 30/06/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 25/06/2020    / CD_LOTE = 2020062501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 25/06/2020 / CD_LOTE_APONTAMENTO = 2020062501 / CD_LOTE = 2020062501

	 * - A manutenção rodou no dia 26/06/2020 as 00:00 e não moveu o caso acima para o histórico.

	 * - Cliente não apontado no dia 08/07/2020 pelo mensal (status = 1) enquadrado.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 1  / DT_ATULZ_CALCULO = 25/06/2020    / CD_LOTE = 2020062501 / FL_SUSP_LD = 0
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 25/06/2020 / CD_LOTE_APONTAMENTO = 2020062501 / CD_LOTE = 2020062501
	
	 * - Verificar se o caso acima aparece na Web.	

	 * - A manutenção rodou no dia 09/07/2020 as 00:00 e moveu o caso acima para o histórico.
	 * @param service
	 * @throws Exception 
	 */
	private void cenarioUm(final DtecLDService service) throws Exception {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario1.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
				if(partes[j].split(";")[i].contains("/* VERIFICAR APARECE WEB */")) {
					webCenarioUm();					
				}
				if(partes[j].split(";")[i].contains("/* EXECUTA MANUTENCAO */")) {
					LOGGER.info("\n\n Executando manutenção");
					LOGGER.info("\n ...");
					KettleJobRunner.runJob();
					LOGGER.info("\n Manutenção finalizada!\n");				
				}
				
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}

	/**
	 * @throws InterruptedException
	 */
	private void webCenarioUm() throws InterruptedException {
//        Thread.sleep(10000);
		LOGGER.info("Iniciando verificação na WEB para o Cenário Um...");
		WebDriver driver = RunWebDriver.getWebDriver();
        driver.get(urlServer);
        driver.manage().window().maximize();
        driver.findElement(By.linkText("DTEC - LD")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.linkText("Consulta e Confirmação")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        boolean continueApagando = true;
        while(continueApagando) {

            driver.findElement(By.id("fila:j_idt106")).click();
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            driver.findElement(By.id("fila:j_idt106")).clear();
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            driver.findElement(By.id("fila:j_idt106")).sendKeys("cenário de teste 1");
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            driver.findElement(By.xpath("//button[@id='fila:btFiltrar']/span[2]")).click();
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            if (!driver.findElements(By.id("fila:tableFila:0:j_idt117")).isEmpty()) {
				driver.findElement(By.id("fila:tableFila:0:j_idt130")).click();
				Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
	            driver.findElement(By.id("fila:j_idt133")).click();
	            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
	            continueApagando = !driver.findElements(By.id("fila:tableFila:0:j_idt117")).isEmpty();
	            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
			} else {
				continueApagando = false;
			}
        }
        
		driver.findElement(By.xpath("//*[@id=\"fila:j_idt112\"]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt142")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt142")).clear();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt142")).sendKeys("cenário de teste 1");
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:grpAcesso']/div/ul/li[4]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:grpAcesso']/div[2]/div/button/span")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//a[@id='formFiltro:j_idt161_toggler']/span")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:j_idt166']/div[3]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt166_2")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:painelCliente']/div")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:campoDocumento")).clear();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:campoDocumento")).sendKeys(Keys.HOME);
        driver.findElement(By.id("formFiltro:campoDocumento")).sendKeys("24089207010");
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:painelCliente']/div[6]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//button[@id='formFiltro:btSalvar']/span[2]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA+1000);
        driver.findElement(By.id("fila:j_idt106")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("fila:j_idt106")).clear();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("fila:j_idt106")).sendKeys("cenário de teste 1");
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//button[@id='fila:btFiltrar']/span[2]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("fila:tableFila:0:j_idt118")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        String result = driver.findElement(By.xpath("//*[@id='apontamento:tableFila_data']/tr/td")).getText();
        assertEquals(result, "Nenhum registro");
        Thread.sleep(5000);
		LOGGER.info("Fim da verificação na WEB para o Cenário Um.");
	}
	
	/**
	 * Cenário de teste 2
	 * Cliente Jose da Silva - 94843592056 *dados*
	 * 	
	 * Descrição do Cenário
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/06/2020 (DT_ATULZ_CALCULO), mas a data do apontamento(DT_APONTAMENTO) ficou no dia 30/06/2020(status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 
	 * - A manutenção rodou no dia 21/06/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 	
	 * - Cliente apontado no dia 08/07/2020 pelo mensal (status = 0) aguardando analise.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 0
	 * 	
	 * - Verificar se o caso acima aparece na Web.	
	 * 
	 * - No mesmo dia a controladoria analisou o caso e finalizou, alterando o status = 20.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 20  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	  / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 20
	 * 
	 * - A manutenção rodou no dia 09/07/2020 as 00:00, e não moveu o caso acima para o histórico porque o status = 20 está configurado com 5 dias (em relação a data do ultimo evento).
	 * 
	 * - No dia 10/07/2020 o cliente foi disparado com data de transação de 30/06/2020
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 10/07/2020    / CD_LOTE = 2020071001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 10/07/2020 / CD_LOTE_APONTAMENTO = 2020071001 / CD_LOTE = 2020071001
	 * 	
	 * - A manutenção rodou no dia 11/07/2020 as 00:00 e não moveu os casos acima para o histórico.
	 * 
	 * - A manutenção rodou no dia 14/07/2020 as 00:00 e moveu para o histórico o apontamento que tinha o evento com status = 20 do dia 08/07/2020.
	 * @param service
	 * @throws Exception 
	 */
	private void cenarioDois(final DtecLDService service) throws Exception {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario2.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
				if(partes[j].split(";")[i].contains("/* MUDAR PARA STATUS 20 NA WEB */")) {
					webCenarioDois();					
				}
				if(partes[j].split(";")[i].contains("/* EXECUTA MANUTENCAO */")) {
					LOGGER.info("\n\n Executando manutenção");
					LOGGER.info("\n ...");
					KettleJobRunner.runJob();
					LOGGER.info("\n Manutenção finalizada!\n");				
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}

	/**
	 * @throws InterruptedException
	 */
	private void webCenarioDois() throws InterruptedException {
        Thread.sleep(5000);
		LOGGER.info("Iniciando verificação na WEB para o Cenário Dois...");
		WebDriver driver = RunWebDriver.getWebDriver();
        driver.get(urlServer);
        driver.manage().window().maximize();
        driver.findElement(By.linkText("DTEC - LD")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.linkText("Consulta e Confirmação")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        boolean continueApagando = true;
        while(continueApagando) {

            driver.findElement(By.id("fila:j_idt106")).click();
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            driver.findElement(By.id("fila:j_idt106")).clear();
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            driver.findElement(By.id("fila:j_idt106")).sendKeys("cenário de teste 2");
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            driver.findElement(By.xpath("//button[@id='fila:btFiltrar']/span[2]")).click();
            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
            if (!driver.findElements(By.id("fila:tableFila:0:j_idt117")).isEmpty()) {
				driver.findElement(By.id("fila:tableFila:0:j_idt130")).click();
				Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
	            driver.findElement(By.id("fila:j_idt133")).click();
	            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
	            continueApagando = !driver.findElements(By.id("fila:tableFila:0:j_idt117")).isEmpty();
	            Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
			} else {
				continueApagando = false;
			}
        }
        
		driver.findElement(By.xpath("//*[@id=\"fila:j_idt112\"]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt142")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt142")).clear();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt142")).sendKeys("cenário de teste 2");
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:grpAcesso']/div/ul/li[4]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:grpAcesso']/div[2]/div/button/span")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//a[@id='formFiltro:j_idt161_toggler']/span")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:j_idt166']/div[3]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:j_idt166_2")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:painelCliente']/div")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("formFiltro:campoDocumento")).sendKeys(Keys.HOME);
        driver.findElement(By.id("formFiltro:campoDocumento")).sendKeys("94843592056");
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//div[@id='formFiltro:painelCliente']/div[6]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//button[@id='formFiltro:btSalvar']/span[2]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA+1000);
        driver.findElement(By.id("fila:j_idt106")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("fila:j_idt106")).clear();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("fila:j_idt106")).sendKeys("cenário de teste 2");
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//button[@id='fila:btFiltrar']/span[2]")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.id("fila:tableFila:0:j_idt118")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//*[@id='apontamento:tableFila:0:j_idt122']")).click();//*[@id="tab"]/ul/li[14]/a
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//*[@id='tab']/ul/li[14]/a")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA+1000);
        driver.findElement(By.xpath("//*[@id='tab:j_idt1598']")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA+1000);
        driver.findElement(By.xpath("//*[@id='tab:formSalvar:j_idt1631']")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//*[@id='tab:formSalvar:j_idt1631_4']")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//*[@id='tab:formSalvar:j_idt1639']")).click();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//*[@id='tab:formSalvar:j_idt1639']")).clear();
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//*[@id='tab:formSalvar:j_idt1639']")).sendKeys("parecer do cenário de teste 2");
        Thread.sleep(TEMPO_ESPERA_COMANDOS_TELA);
        driver.findElement(By.xpath("//*[@id='tab:formSalvar:j_idt1644']")).click();
        Thread.sleep(5000);
		LOGGER.info("Fim da verificação na WEB para o cenário Dois.");
	}
	
	/**
	 * Cenário de teste 3
	 * 
	 * Cliente Maria Conceição - 72887707027 *dados*
	 * 	
	 * Descrição do Cenário
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/06/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 30/06/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 
	 * - A manutenção rodou no dia 21/06/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 	
	 * - Cliente apontado no dia 08/07/2020 pelo mensal (status = 0) aguardando analise.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 0
	 * 	
	 * - Verificar se o caso acima aparece na Web.	
	 * 
	 * - No mesmo dia a controladoria analisou o caso e finalizou, alterando o status = 5.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 5  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 5
	 * 
	 * - A manutenção rodou no dia 09/07/2020 as 00:00, e não moveu o caso acima para o histórico porque não é um status finalizador.
	 * 
	 * - No dia 10/07/2020 o cliente foi disparado com data de transação de 30/06/2020
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 5  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 (*mudar CD_LOTE, para CD_LOTE_APONTAMENTO)
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 10/07/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020071001
	 * 	
	 * - A manutenção rodou no dia 11/07/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 
	 * - No dia 15/07/2020 a controladoria analisou o caso e finalizou, alterando o status = 90.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 90  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 15/07/2020	 / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 90
	 * 
	 * - A manutenção rodou no dia 16/07/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 	
	 * - A manutenção rodou no dia 21/07/2020 as 00:00 e moveu para o histórico o apontamento que tinha o evento com status = 90 do dia 15/07/2020.
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioTres(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario3.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 4
	 * 
	 * Cliente Julia Prado - 40729010082 *dados*
	 * 	
	 * Descrição do Cenário
	 * 
	 * - Cliente disparado pela regra 20 e 51 (conceito - Gravissima) no dia 20/05/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 31/05/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/05/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/05/2020    / CD_LOTE = 2020052001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/05/2020 / DT_DISP_REGRA = 20/05/2020 / CD_LOTE_APONTAMENTO = 2020052001 / CD_LOTE = 2020052001
	 * 
	 * - A manutenção rodou no dia 21/05/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 	
	 * - Cliente apontado no dia 08/06/2020 pelo mensal (status = 0) aguardando analise.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/05/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 20/05/2020    / CD_LOTE = 2020052001 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/05/2020 / DT_DISP_REGRA = 20/05/2020 / CD_LOTE_APONTAMENTO = 2020052001 / CD_LOTE = 2020052001
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 31/05/2020 / DT_EVENTO = 08/06/2020	 / CD_LOTE_APONTAMENTO = 2020052001 / CD_STTUS_EVENTO = 0
	 * 	
	 * - Verificar se o caso acima aparece na Web.	
	 * 
	 * - Cliente enviado para o historico no dia 08/07/2020 pelo mensal, o cliente será excluido da TB_APONTAMENTO / TB_DETLH_APONTAMENTO / TB_EVENTO.
	 * 	TB_EVENTO			-       DT_APONTAMENTO = 31/05/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020052001 / CD_STTUS_EVENTO = 30 (movido pelo sistema)*OBS.:Esta linha será apagada.
	 * 	TB_APONTAMENTO_HIST		- 	DT_APONTAMENTO = 31/05/2020 / CD_STTUS_EVENTO_ATUAL = 30  / DT_ATULZ_CALCULO = 20/05/2020    / CD_LOTE = 2020052001 / FL_SUSP_LD = 1 / DT_HISTORICO = 08/07/2020
	 * 	TB_DETLH_APONTAMENTO_HIST	-	DT_APONTAMENTO = 31/05/2020 / DT_DISP_REGRA = 20/05/2020 / CD_LOTE_APONTAMENTO = 2020052001 / CD_LOTE = 2020052001 / DT_HISTORICO = 08/07/2020
	 * 	TB_EVENTO_HIST			-       DT_APONTAMENTO = 31/05/2020 / DT_EVENTO = 08/06/2020	 / CD_LOTE_APONTAMENTO = 2020052001 / CD_STTUS_EVENTO = 0  / DT_HISTORICO = 08/07/2020
	 * 	TB_EVENTO_HIST			-       DT_APONTAMENTO = 31/05/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020052001 / CD_STTUS_EVENTO = 30  / DT_HISTORICO = 08/07/2020
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioQuatro(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario4.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 5
	 * 
	 * Cliente Marcia Santos - 01041336004 *dados*
	 * 	
	 * Descrição do Cenário
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/06/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 30/06/2020(status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 
	 * - A manutenção rodou no dia 21/06/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 	
	 * - Cliente apontado no dia 08/07/2020 pelo mensal (status = 0) aguardando analise.
	 * 	TB_APONTAMENTO		-   DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-   DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 	TB_EVENTO		-   DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020     / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 0
	 * 	
	 * - Verificar se o caso acima aparece na Web.	
	 * 
	 * - No mesmo dia a controladoria analisou o caso e finalizou, alterando o status = 20.
	 * 	TB_APONTAMENTO		-   DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 20  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-   DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020      / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 20
	 * 
	 * - A manutenção rodou no dia 09/07/2020 as 00:00, e não moveu o caso acima para o histórico porque o status = 20 está configurado com 5 dias (em relação a data do ultimo evento).
	 * 
	 * - No dia 10/07/2020 o cliente foi disparado pela regra 20 e 5 com data de transação de 10/07/2020
	 * 	TB_APONTAMENTO		-   DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 10/07/2020    / CD_LOTE = 2020071001
	 * 	TB_DETLH_APONTAMENTO	-   DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 10/07/2020 / CD_LOTE_APONTAMENTO = 2020071001 / CD_LOTE = 2020071001
	 * 	
	 * - A manutenção rodou no dia 11/07/2020 as 00:00 e não moveu os casos acima para o histórico.
	 * 
	 * - A manutenção rodou no dia 14/07/2020 as 00:00 e moveu para o histórico o apontamento de Junho que já estava finalizado.
	 *   O apontamento de Julho não foi movido para o histórico.
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioCinco(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario5.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 6
	 * 
	 * Cliente Bruna Magalhães - 26273329028 *dados*
	 * 	
	 * Descrição do Cenário
	 * 
	 * - No dia 15/07/2020 a controladoria pesquisou a cliente na tela de Consulta Cliente e adicionou o status = 95.
	 * 	TB_APONTAMENTO		-   DT_APONTAMENTO = 15/07/2020 / CD_STTUS_EVENTO_ATUAL = 95  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 0 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-   DT_APONTAMENTO = 15/07/2020 / DT_DISP_REGRA = 15/07/2020  / CD_LOTE_APONTAMENTO = 0 (Web não pode colocar null) / CD_LOTE = 0
	 * 	TB_EVENTO		-   DT_APONTAMENTO = 15/07/2020 / DT_EVENTO = 15/07/2020      / CD_LOTE_APONTAMENTO = 0 (Web não pode colocar null) / CD_STTUS_EVENTO = 95
	 * 	
	 * - A manutenção rodou no dia 16/07/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 
	 * - A manutenção rodou no dia 21/07/2020 as 00:00 e moveu para o histórico o apontamento que tinha o evento com status = 95 do dia 15/07/2020. 
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioSeis(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario6.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 7
	 * 
	 * Cliente Joaquim Silva - 44770224095 *dados*
	 * 	
	 * Descrição do Cenário
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/06/2020 (DT_ATULZ_CALCULO), mas a data do apontamento(DT_APONTAMENTO) ficou no dia 30/06/2020(status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 
	 * - A manutenção rodou no dia 21/06/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 	
	 * - No dia 02/07/2020 o cliente foi disparado com data de transação de 30/06/2020
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 02/07/2020    / CD_LOTE = 2020062001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 02/07/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020070201
	 * 	
	 * - A manutenção rodou no dia 03/07/2020 as 00:00 e não moveu o caso acima para o histórico.	
	 * 
	 * - Cliente apontado no dia 08/07/2020 pelo mensal (status = 0) aguardando analise.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 0
	 * 	
	 * - Verificar se o caso acima aparece na Web.	
	 * 
	 * - No mesmo dia a controladoria analisou o caso e finalizou, alterando o status = 20.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 20  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	  / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 20
	 * 
	 * - A manutenção rodou no dia 09/07/2020 as 00:00, e não moveu o caso acima para o histórico porque o status = 20 está configurado com 5 dias (em relação a data do ultimo evento).
	 * 
	 * - A manutenção rodou no dia 14/07/2020 as 00:00 e moveu para o histórico o apontamento que tinha o evento com status = 20 do dia 08/07/2020.
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioSete(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario7.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 8
	 * 
	 * Cliente Camila Realengo - 56065015032 *dados*
	 * 	
	 * Descrição do Cenário
	 * 	
	 * - No dia 02/07/2020 o cliente foi disparado com data de transação de 30/06/2020 e 01/07/2020
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 02/07/2020    / CD_LOTE = 2020070201
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 02/07/2020    / CD_LOTE = 2020070201
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 02/07/2020 / CD_LOTE_APONTAMENTO = 2020070201 / CD_LOTE = 2020070201
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 02/07/2020 / CD_LOTE_APONTAMENTO = 2020070201 / CD_LOTE = 2020070201
	 * 	
	 * - A manutenção rodou no dia 03/07/2020 as 00:00 e não moveu o caso acima para o histórico.	
	 * 
	 * - Cliente apontado no dia 08/07/2020 pelo mensal (status = 0) aguardando analise.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 02/07/2020    / CD_LOTE = 2020070201 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 02/07/2020 / CD_LOTE_APONTAMENTO = 2020070201 / CD_LOTE = 2020070201
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020070201 / CD_STTUS_EVENTO = 0
	 * 	
	 * - Verificar se o caso acima aparece na Web.	
	 * 
	 * - No mesmo dia a controladoria analisou o caso e finalizou, alterando o status = 20.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 20  / DT_ATULZ_CALCULO = 02/07/2020    / CD_LOTE = 2020070201 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	  / CD_LOTE_APONTAMENTO = 2020070201 / CD_STTUS_EVENTO = 20
	 * 
	 * - A manutenção rodou no dia 09/07/2020 as 00:00, e não moveu o caso acima para o histórico porque o status = 20 está configurado com 5 dias (em relação a data do ultimo evento).
	 * 
	 * - A manutenção rodou no dia 14/07/2020 as 00:00 e moveu para o histórico o apontamento que tinha o evento com status = 20 do dia 08/07/2020.
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioOito(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario8.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}

	/**
	 * Cenário de teste 9
	 * 
	 * Cliente Lucas Barros de Souza - 07691169060 *dados*
	 * 	
	 * Descrição do cenário.
	 * 
	 * - Processamento mensal ocorreu no dia 08/07/2020
	 * 
	 * - No dia 15/07/2020 o cliente foi disparado pela regra 20 e 51 pela transação do dia 25/06/2020*
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 15/07/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 30/06/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 
	 * - A manutenção rodou no dia 16/07/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 
	 * - Esse caso não deve aparecer na Web.	
	 * 
	 * - O mensal rodou no dia 09/08/2020 e moveu o caso acima para o histórico com Status Evento = 2.
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioNove(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario9.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 10
	 * 
	 * Cliente Silvana Coimbra - 26478946045 *dados*
	 * 	
	 * Descrição do cenário.
	 * 
	 * - Processamento mensal ocorreu no dia 08/07/2020 
	 * 
	 * - Esse caso não deve aparecer na Web.
	 * 
	 * - No dia 15/07/2020 o cliente foi disparado pela regra 20 e 51 pela transação do dia 25/06/2020*
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 15/07/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 30/06/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 
	 * - A manutenção rodou no dia 16/07/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 
	 * - No dia 20/07/2020 o cliente foi disparado pela regra 20 e 51 pela transação do dia 20/07/2020*
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/07/2020 (DT_ATULZ_CALCULO), e a data do apontamento (DT_APONTAMENTO) ficou no dia 31/07/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072001
	 * 
	 * - O mensal rodou no dia 09/08/2020 e moveu o caso de Jun/2020 para o histórico com Status Evento = 2 e manteve o apontamento de Jul/2020.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072001
	 * 
	 * - No mesmo dia a controladoria analisou o caso e finalizou, alterando o status = 20.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 20  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 31/07/2020 / DT_EVENTO = 08/07/2020	  / CD_LOTE_APONTAMENTO = 2020072001 / CD_STTUS_EVENTO = 20
	 * 
	 * - A manutenção rodou no dia 09/08/2020 as 00:00, e não moveu o caso acima para o histórico porque o status = 20 está configurado com 5 dias (em relação a data do ultimo evento).
	 * 
	 * - A manutenção rodou no dia 14/08/2020 as 00:00 e moveu para o histórico o apontamento que tinha o evento com status = 20 do dia 08/08/2020.
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioDez(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario10.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 11
	 * 
	 * Cliente Paula da Costa Souza - 15555497059 *dados*
	 * 	
	 * Descrição do Cenário
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/06/2020 (DT_ATULZ_CALCULO), e a data do apontamento(DT_APONTAMENTO) ficou no dia 30/06/2020(status = 2).
	 * 	TB_APONTAMENTO		    - 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 
	 * - A manutenção rodou no dia 21/06/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 	
	 * - Cliente apontado no dia 08/07/2020 pelo mensal (status = 0) aguardando analise.
	 * 	TB_APONTAMENTO		    - 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 	TB_EVENTO		        -   DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 0
	 * 	
	 * - Verificar se o caso acima aparece na Web.	
	 * 
	 * - No mesmo dia a controladoria pegou o caso para analisar, alterando o status = 5 (em análise).
	 * 	TB_APONTAMENTO	- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 5   / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1	
	 * 	TB_EVENTO		-   DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	  / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 20
	 * 
	 * - A manutenção rodou no dia 09/07/2020 as 00:00, e não moveu o caso acima para o histórico porque o status = 5 está configurado com 5 dias (em relação a data do ultimo evento).
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 25/07/2020 (DT_ATULZ_CALCULO), e a data do apontamento(DT_APONTAMENTO) ficou no dia 31/07/2020(status = 2).
	 * 	TB_APONTAMENTO		    - 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 25/07/2020    / CD_LOTE = 2020072501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072501 / CD_LOTE = 2020072501
	 * 
	 * - A manutenção rodou no dia 21/06/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 
	 * - Cliente apontado no dia 08/08/2020 pelo mensal, agregando os disparos de 25/07/2020 no apontamento de 30/06/2020.
	 * 	TB_APONTAMENTO		    - 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 5  / DT_ATULZ_CALCULO = 20/06/2020    / CD_LOTE = 2020062001 / FL_SUSP_LD = 1
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/06/2020 / CD_LOTE_APONTAMENTO = 2020062001 / CD_LOTE = 2020062001
	 * 	TB_EVENTO		        -   DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 08/07/2020	 / CD_LOTE_APONTAMENTO = 2020062001 / CD_STTUS_EVENTO = 0
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioOnze(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario11.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}

	/**
	 * Cenário de teste 12
	 * 
	 * Cliente Amanda Mello - 39966361057 *dados*
	 * 	
	 * Descrição do cenário.
	 * 
	 * - No dia 15/07/2020 o cliente foi disparado pela regra 20 e 51 pela transação do dia 25/06/2020*
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 15/07/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 30/06/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 
	 * - A manutenção rodou no dia 16/07/2020 as 00:00 e não moveu o caso acima para o histórico.
	 * 
	 * - No dia 20/07/2020 o cliente foi disparado pela regra 20 e 51 pela transação do dia 20/07/2020*
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/07/2020 (DT_ATULZ_CALCULO), e a data do apontamento (DT_APONTAMENTO) ficou no dia 31/07/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072001
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 04/08/2020 (DT_ATULZ_CALCULO), e a data do apontamento (DT_APONTAMENTO) ficou no dia 31/08/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072001
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/08/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 04/08/2020    / CD_LOTE = 2020080401
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/08/2020 / DT_DISP_REGRA = 04/08/2020 / CD_LOTE_APONTAMENTO = 2020080401 / CD_LOTE = 2020080401
	 * 
	 * - O mensal rodou no dia 09/08/2020 e moveu o caso de Jun/2020 para o histórico com Status Evento = 2 e manteve o apontamento de Jul/2020 e agosto.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072001
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/08/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 04/08/2020    / CD_LOTE = 2020080401
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/08/2020 / DT_DISP_REGRA = 04/08/2020 / CD_LOTE_APONTAMENTO = 2020080401 / CD_LOTE = 2020080401
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioDoze(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario12.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
				if(partes[j].split(";")[i].contains("/* LIGAR O TRANSPORTE HIST E RODAR O MENSAL DE JULHO */")) {
					Configuracao.props_conf.setProperty("desligar_transp_hist", "false");					
				}
				if(partes[j].split(";")[i].contains("/* DESLIGAR O TRANSPORTE HIST E RODAR O MENSAL DE JULHO NOVAMENTE */")) {
					Configuracao.props_conf.setProperty("desligar_transp_hist", "true");					
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}
	
	/**
	 * Cenário de teste 13
	 * 
	 * Cliente Armando Castilho - 71044301058 *dados*
	 * 
	 * Descrição do cenário.
	 * 
	 * - No dia 15/07/2020 o cliente foi disparado pela regra 20 e 51 pela transação do dia 25/06/2020*
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 15/07/2020 (DT_ATULZ_CALCULO), mas a data do apontamento (DT_APONTAMENTO) ficou no dia 30/06/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 
	 * - O mensal rodou no dia 16/07/2020 e manteve o apontamento de Jun/2020.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 
	 * - No mesmo dia a controladoria analisou o caso e mudou o status para 5.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 5  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501 / FL_SUSP_LD = 1
	 * 	TB_EVENTO		-       DT_APONTAMENTO = 30/06/2020 / DT_EVENTO = 16/07/2020	  / CD_LOTE_APONTAMENTO = 2020071501 / CD_STTUS_EVENTO = 5
	 * 
	 * - No dia 20/07/2020 o cliente foi disparado pela regra 20 e 51 pela transação do dia 20/07/2020*
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 20/07/2020 (DT_ATULZ_CALCULO), e a data do apontamento (DT_APONTAMENTO) ficou no dia 31/07/2020 (status = 2).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072001
	 * 
	 * - Cliente disparado pela regra 20 e 51 no dia 25/07/2020 (DT_ATULZ_CALCULO), e a data do apontamento (DT_APONTAMENTO) ficou no dia 31/07/2020 (status = 2 agregado).
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 31/07/2020 / CD_STTUS_EVENTO_ATUAL = 2  / DT_ATULZ_CALCULO = 20/07/2020    / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 31/07/2020 / DT_DISP_REGRA = 25/07/2020 / CD_LOTE_APONTAMENTO = 2020072001 / CD_LOTE = 2020072501
	 * 
	 * - O mensal rodou no dia 09/08/2020 e agregou o caso de Jun/2020 com o de Jul/2020.
	 * 	TB_APONTAMENTO		- 	DT_APONTAMENTO = 30/06/2020 / CD_STTUS_EVENTO_ATUAL = 0  / DT_ATULZ_CALCULO = 15/07/2020    / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 15/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020071501
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 20/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020072001
	 * 	TB_DETLH_APONTAMENTO	-	DT_APONTAMENTO = 30/06/2020 / DT_DISP_REGRA = 25/07/2020 / CD_LOTE_APONTAMENTO = 2020071501 / CD_LOTE = 2020072501
	 * @param service
	 * @throws SQLException
	 */
	private void cenarioTreze(final DtecLDService service) throws SQLException {
		
		final Statement statement = jdbcConnection.createStatement();	

		String query = carregarQuery(CarregarQuery.Origem.CENARIOS, "cenario13.sql");
		
		String[] partes = query.split("\\/\\* EXECUTAR ANALISE \\*\\/");
		
		for (int j = 0; j < partes.length; j++) {
			for (int i = 0; i < partes[j].trim().split(";").length; i++) {
				statement.addBatch(partes[j].split(";")[i]);
				try {
					statement.executeBatch();
					statement.clearBatch();
				} catch (Exception e) {
					if(e.getMessage().contains("sqlKind = UNINITIALIZED")) {
						LOGGER.warn("Sujeira no script, ignorando...\nQuery executada:\n[" + partes[j].split(";")[i] + "]");
					} else {
						LOGGER.warn("Erro na seguinte query executada:\n[" + partes[j].split(";")[i] + "]\nDetalhes do Erro:");
						e.printStackTrace();
						LOGGER.warn("Ignorando...\n");
					}
				}
				if(partes[j].split(";")[i].contains("/* LIGAR O TRANSPORTE HIST E RODAR O MENSAL DE JULHO */")) {
					Configuracao.props_conf.setProperty("desligar_transp_hist", "false");					
				}
				if(partes[j].split(";")[i].contains("/* DESLIGAR O TRANSPORTE HIST E RODAR O MENSAL DE JULHO NOVAMENTE */")) {
					Configuracao.props_conf.setProperty("desligar_transp_hist", "true");					
				}
			}

			service.execute(ModoExecucao.TESTE);
		} 
		
	}

	public void limpaTeste(String lote) throws SQLException {

		final Statement statement = jdbcConnection.createStatement();

		String query = carregarQuery(CarregarQuery.Origem.TESTE, "limpa_teste.sql");

		for (int i = 0; i < query.trim().split(";").length; i++) {
			statement.addBatch(query.split(";")[i].replaceAll(":cd_lote", lote));
		}
		try {
			statement.executeBatch();
		} catch (Exception e) {
			LOGGER.warn("Erro ao deletar, ignorando..." + e);
			e.printStackTrace();
			System.exit(0);
		}
	}

    @After
    public void tearDown() {
        WebDriverManager.shutDownDriver();
    }

}
