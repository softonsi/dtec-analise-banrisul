package br.com.softon.dtec.service;

import static br.com.softon.dtec.utils.CarregarQuery.carregarQuery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

public class DtecLDServiceTestBLL {

	private final static Logger LOGGER = LoggerFactory.getLogger(DtecLDServiceTestBLL.class);

	private Connection jdbcConnection;
	private Queue<String> lotes;

	private BufferedReader br;
	private BufferedWriter bw;
	private BufferedWriter bwRegras4001;
	private StringBuilder bupdates;
	
	private String temp;

	@Before
	public void setup()
			throws Exception {
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

		Configuracao.props_conf.setProperty("gravarLogPerformance", "false");
		Configuracao.props_conf.setProperty("desligar_job_kettle", "true");
		
		Configuracao.props_conf.setProperty("desligar_regras_conclusivas", "false");
		
		Configuracao.props_conf.setProperty("desligar_criacao_lote_mensal", "false");

		Configuracao.props_conf.setProperty("qtd_rows_per_page", "2000");

		Configuracao.props_conf.setProperty("wait_next_lote_seconds", "10000");

		Configuracao.props_conf.setProperty("tables-to-gather-stats", "TB_TRANS,TB_CAD_CLIE");

		Class.forName(driver);

		jdbcConnection = DriverManager.getConnection(url, usr, pwd);
		lotes = new LinkedList<String>();

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
		
		temp = "in (";

		Writer writer = new FileWriter("regras-bll-diaria/update_bll.sql");
		bw = new BufferedWriter(writer);
		bw.write("set define off; \n");
		bw.write("DECLARE \n");
		bupdates.append("\n BEGIN");
		
		//  Apagar os arquivos sql gerados no processamento anterior com parametros...
		deleteTree(new File("regras-bll-diaria/regras-com-params"));
		
		Writer wrRegras4001 = new FileWriter("regras-bll-diaria/regras-com-params/insert-regras-4001-rollback.sql");
		bwRegras4001 = new BufferedWriter(wrRegras4001);
		bwRegras4001.write("ALTER TABLE TB_DETLH_APONTAMENTO MODIFY CONSTRAINT \"FkApontamento_DetlhApontamento\" DISABLE; \n\n\n");

//		updateRegra("regras-bll-diaria/REGRA 020.drl");
//		updateRegra("regras-bll-diaria/REGRA 022.drl");
//		updateRegra("regras-bll-diaria/REGRA 024.drl");
//		updateRegra("regras-bll-diaria/REGRA 025.drl");
//		updateRegra("regras-bll-diaria/REGRA 026.drl");
//		updateRegra("regras-bll-diaria/REGRA 027.drl");
//		updateRegra("regras-bll-diaria/REGRA 030.drl");
//		updateRegra("regras-bll-diaria/REGRA 031.drl");
//		updateRegra("regras-bll-diaria/REGRA 032.drl");
//		updateRegra("regras-bll-diaria/REGRA 033.drl");
//		updateRegra("regras-bll-diaria/REGRA 038.drl");
//		updateRegra("regras-bll-diaria/REGRA 043.drl");
//		updateRegra("regras-bll-diaria/REGRA 051.drl");
//		updateRegra("regras-bll-diaria/REGRA 052.drl");
//		updateRegra("regras-bll-diaria/REGRA 060.drl");
//		updateRegra("regras-bll-diaria/REGRA 065.drl");
//		updateRegra("regras-bll-diaria/REGRA 070.drl");
//		updateRegra("regras-bll-diaria/REGRA 071.drl");
//		updateRegra("regras-bll-diaria/REGRA 073.drl");
//		updateRegra("regras-bll-diaria/REGRA 074.drl");
//		updateRegra("regras-bll-diaria/REGRA 075.drl");
//		updateRegra("regras-bll-diaria/REGRA 076.drl");
//		updateRegra("regras-bll-diaria/REGRA 080.drl");
//		updateRegra("regras-bll-diaria/REGRA 081.drl");
//		updateRegra("regras-bll-diaria/REGRA 082.drl");
//		updateRegra("regras-bll-diaria/REGRA 083.drl");
//		updateRegra("regras-bll-diaria/REGRA 084.drl");
//		updateRegra("regras-bll-diaria/REGRA 085.drl");
//		updateRegra("regras-bll-diaria/REGRA 086.drl");
//		updateRegra("regras-bll-diaria/REGRA 087.drl");
//		updateRegra("regras-bll-diaria/REGRA 088.drl");
//		updateRegra("regras-bll-diaria/REGRA 089.drl");
//		updateRegra("regras-bll-diaria/REGRA 090.drl");
//		updateRegra("regras-bll-diaria/REGRA 091.drl");
//		updateRegra("regras-bll-diaria/REGRA 092.drl");
//		updateRegra("regras-bll-diaria/REGRA 094.drl");
//		updateRegra("regras-bll-diaria/REGRA 120.drl");
//		updateRegra("regras-bll-diaria/REGRA 124.drl");
//		updateRegra("regras-bll-diaria/REGRA 125.drl");
//		updateRegra("regras-bll-diaria/REGRA 126.drl");
//		updateRegra("regras-bll-diaria/REGRA 210.drl");
//		updateRegra("regras-bll-diaria/REGRA 402.drl");
//		updateRegra("regras-bll-diaria/REGRA 403.drl");
//		updateRegra("regras-bll-diaria/REGRA 405.drl");		
//		updateRegra("regras-bll-4001/diaria/REGRA 4001.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4006.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4007.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4009.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4010.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4011.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4012.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4013.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4015.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4016.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4017.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4018.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4019.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4020.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4021.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4023.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4034.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4035.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4036.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4037.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4061.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4062.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4063.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4067.drl"); 
//		updateRegra("regras-bll-4001/diaria/REGRA 4068.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4091.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4092.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4093.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4094.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4113.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4115.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4118.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4124.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4141.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4142.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4143.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4144.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4145.drl");
//		updateRegra("regras-bll-4001/diaria/REGRA 4146.drl");

//		updateRegra("regras-bll-4001/mensal/REGRA 4002.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4003.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4004.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4005.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4008.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4014.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4022.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4059.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4060.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4064.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4065.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4066.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4086.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4087.drl");
//		updateRegra("regras-bll-4001/mensal/REGRA 4088.drl");
//		updateRegra("regras-bll-4001/mensal/REGRA 4089.drl");
		updateRegra("regras-bll-4001/mensal/REGRA 4090.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4099.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4100.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4101.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4102.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4114.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4116.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4117.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4132.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4137.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4138.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4139.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4140.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4147.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4148.drl"); 
//		updateRegra("regras-bll-4001/mensal/REGRA 4149.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4150.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4151.drl");	
//		updateRegra("regras-bll-4001/mensal/REGRA 4201.drl");		
//		updateRegra("regras-bll-mensal/REGRA 000.drl");
//		updateRegra("regras-bll-mensal/REGRA 012.drl");
//		updateRegra("regras-bll-mensal/REGRA 021.drl");
//		updateRegra("regras-bll-mensal/REGRA 023.drl");
//		updateRegra("regras-bll-mensal/REGRA 041.drl");
//		updateRegra("regras-bll-mensal/REGRA 044.drl");
//		updateRegra("regras-bll-mensal/REGRA 045.drl");
//		updateRegra("regras-bll-mensal/REGRA 046.drl");
//		updateRegra("regras-bll-mensal/REGRA 047.drl");
//		updateRegra("regras-bll-mensal/REGRA 048.drl");
//		updateRegra("regras-bll-mensal/REGRA 050.drl");
//		updateRegra("regras-bll-mensal/REGRA 053.drl");
//		updateRegra("regras-bll-mensal/REGRA 054.drl");
//		updateRegra("regras-bll-mensal/REGRA 055.drl");
//		updateRegra("regras-bll-mensal/REGRA 056.drl");
//		updateRegra("regras-bll-mensal/REGRA 057.drl");
//		updateRegra("regras-bll-mensal/REGRA 058.drl");
//		updateRegra("regras-bll-mensal/REGRA 061.drl");
//		updateRegra("regras-bll-mensal/REGRA 063.drl");
//		updateRegra("regras-bll-mensal/REGRA 067.drl");
//		updateRegra("regras-bll-mensal/REGRA 068.drl");
//		updateRegra("regras-bll-mensal/REGRA 069.drl");
//		updateRegra("regras-bll-mensal/REGRA 093.drl");
//		updateRegra("regras-bll-mensal/REGRA 101.drl");
//		updateRegra("regras-bll-mensal/REGRA 102.drl");
//		updateRegra("regras-bll-mensal/REGRA 103.drl");
//		updateRegra("regras-bll-mensal/REGRA 104.drl");
//		updateRegra("regras-bll-mensal/REGRA 110.drl");
//		updateRegra("regras-bll-mensal/REGRA 111.drl");
//		updateRegra("regras-bll-mensal/REGRA 112.drl");
//		updateRegra("regras-bll-mensal/REGRA 160.drl");
//		updateRegra("regras-bll-mensal/REGRA 161.drl");
//		updateRegra("regras-bll-mensal/REGRA 400.drl");
//		updateRegra("regras-bll-mensal/REGRA 401.drl");
//		updateRegra("regras-bll-mensal/REGRA 404.drl");
//		updateRegra("regras-bll-mensal/REGRA 049.drl");
//		updateRegra("regras-bll-mensal/REGRA 042.drl");
//		updateRegra("regras-bll-mensal/REGRA 040.drl");
//		updateRegra("regras-bll-mensal/REGRA 034.drl");
//		updateRegra("regras-bll-mensal/REGRA 035.drl");
//		updateRegra("regras-bll-mensal/REGRA 036.drl");
//		updateRegra("regras-bll-mensal/REGRA 037.drl");
//		updateRegra("regras-bll-mensal/REGRA 204.drl");
//		updateRegra("regras-bll-mensal/REGRA 205.drl");
//		updateRegra("regras-bll-mensal/REGRA 206.drl");
//		updateRegra("regras-bll-mensal/REGRA 207.drl");
//		updateRegra("regras-bll-mensal/REGRA 208.drl");
//		updateRegra("regras-bll-mensal/REGRA 209.drl");
//		updateRegra("regras-bll-mensal/REGRA 211.drl");
//		updateRegra("regras-bll-mensal/REGRA 212.drl");
//		updateRegra("regras-bll-mensal/REGRA 213.drl");
//		updateRegra("regras-bll-mensal/REGRA 214.drl");
//		updateRegra("regras-bll-mensal/REGRA 215.drl");
//		updateRegra("regras-bll-mensal/REGRA 216.drl");
//		updateRegra("regras-bll-mensal/REGRA 301.drl");
//		updateRegra("regras-bll-mensal/REGRA 302.drl");
//		updateRegra("regras-bll-mensal/REGRA 303.drl");
//		updateRegra("regras-bll-mensal/REGRA 304.drl");
//		updateRegra("regras-bll-mensal/REGRA 305.drl");
//		updateRegra("regras-bll-mensal/REGRA 310.drl");
//		updateRegra("regras-bll-mensal/REGRA 311.drl");
//		updateRegra("regras-bll-mensal/REGRA 312.drl");
		
		bupdates.append("\n end;\n/\nCOMMIT;");
		bw.write(bupdates.toString());   
		

		bwRegras4001.write("ALTER TABLE TB_DETLH_APONTAMENTO MODIFY CONSTRAINT \"FkApontamento_DetlhApontamento\" ENABLE; \n");
		
		temp += ");";  
		
//		System.out.println(temp);

		bw.flush();
		bw.close();

		bwRegras4001.flush();
		bwRegras4001.close();

		LOGGER.info("END SETUP");
	}

	/**
	 *  Ativa e atualiza regras para execução do teste
	 * @param regra - Caminho completo para a regra
	 * @throws Exception
	 */
	protected void updateRegra(String regra) throws Exception {
		try {
			File file = new File(regra);
			Reader reader = new FileReader(file);
			br = new BufferedReader(reader);
			String nextLine = "";
			StringBuffer sb = new StringBuffer();
			StringBuffer sbls = new StringBuffer();
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine).append(System.lineSeparator());
				sbls.append(nextLine).append(System.lineSeparator());
			}
			
			String query = "update tb_regra" +
						"	set" +
						"		fl_regra_ativa = 1," +
						"		tx_regra_dinamica = ?, dt_regra = sysdate" +
						"	where" +
						"		cd_regra = ?";
			
			int codRegra = Integer.parseInt(regra.substring(regra.lastIndexOf("/", regra.length())).replaceAll("[^0-9]", ""));
			
			temp += codRegra + ",";

			final PreparedStatement ps = jdbcConnection.prepareStatement(query);
			ps.setString(1, sb.toString());
			ps.setInt(2, codRegra);

			ps.execute();
			
			createSQLRegraWithSelectParams((short) codRegra, sbls);

			String banco = Configuracao.props_conn.getProperty("banco");

			if ("ORACLE".equalsIgnoreCase( banco )) {
				
				query = "REGRA" + codRegra + " Clob := ";
	
				bupdates.append("\n update tb_regra set dt_regra_dinamica = sysdate, DS_ERRO_REGRA = NULL, tx_regra_dinamica = REGRA" + codRegra + " where cd_regra = " + codRegra + ";");

				
				String str = sb.toString().replace("'", "''").replace(System.lineSeparator(), " @quebra@'||'");
				str = str.replace("@quebra@", "\n");
	
				bw.append("\n" + query + "'" + str + "';");
			} else {
				query = "update tb_regra set dt_regra = getdate(), tx_regra_dinamica = ? where cd_regra = " + codRegra;
				
				String str = sb.toString().replace(System.lineSeparator(), "");

				bw.append(query.replace("?", "'" + str + "'") + ";\n");
			}


		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param regra
	 * @param sb
	 * @throws Exception
	 */
	private void createSQLRegraWithSelectParams(Short regra, StringBuffer sb) throws Exception {
		String sqlRegra = sb.toString();
		
		final Collection<ParametrosRegras> listaParametrosRegras = ObjetoAnaliseDAO.getParams(new Regra(regra, regra >= 4000 && regra < 5000 ? (byte) 4 : (byte) 3), new ControleSimulacao(), false);
		
		sqlRegra = "INSERT INTO TB_DETLH_APONTAMENTO (ID_DETLH_APONTAMENTO, DT_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_LOTE, CD_REGRA, DT_DISP_REGRA, "
				+ "CD_VERSAO_SISTEMA, CD_LOTE_APONTAMENTO, DS_INF_ANLSE)\nSELECT SQ_DETLH_APONT.NEXTVAL, DT_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, 2020092201 CD_LOTE, " 
				+ regra + " CD_REGRA, SYSDATE DT_DISP_REGRA, 4 CD_VERSAO_SISTEMA, 2020092201 CD_LOTE_APONTAMENTO, DS_INF_ANLSE FROM (\n" + 
						sqlRegra.replaceAll(":cd_lote", "2020092201").replaceAll(":cd_sublote", "1");
				
		if (!listaParametrosRegras.isEmpty()) {
			for (ParametrosRegras param : listaParametrosRegras) {
				sqlRegra = sqlRegra.replaceAll(":" + param.getNomeCampo(), "(select vl_param from tb_regra_parametro where cd_regra = " + regra + " and nm_campo_param = '"+param.getNomeCampo()+"')");
				sqlRegra = sqlRegra.replaceAll(":DS-" + param.getNomeCampo(), "(select DS_CAMPO_PARAM from tb_regra_parametro where cd_regra = " + regra + " and nm_campo_param = ''"+param.getNomeCampo()+"'')");
				sqlRegra = sqlRegra.replaceAll(":LS-" + param.getNomeCampo(), "(select DS_PARAM from tb_regra_parametro where cd_regra = " + regra + " and nm_campo_param = ''"+param.getNomeCampo()+"'')");
				sqlRegra = sqlRegra.replaceAll(":NM-" + param.getNomeCampo(), "(select NM_CAMPO_PARAM from tb_regra_parametro where cd_regra = " + regra + " and nm_campo_param = ''"+param.getNomeCampo()+"'')");
			} 
		}

		Writer w = new FileWriter("regras-bll-diaria/regras-com-params/regra-"+ regra +".sql");
		BufferedWriter bwriter = new BufferedWriter(w);
		bwriter.write(sqlRegra + ";\nROLLBACK;");
		
		if(regra >= 4000 && regra < 5000) bwRegras4001.write(sqlRegra + ");\nROLLBACK;\n\n");

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
		// Caso queira apenas gerar as queries das regras com os parâmetros preenchidos basta descomentar o código abaixo...
		// Lembrando que as queries das regras serão geradas de acordo com as regras que estiverem descomentadas na parte de setup...
		// Outro detalhe é que a cada nova execução deste teste, a pasta "regras-com-params" é limpa...
//		System.exit(0);

		final DtecLDService service = new DtecLDService();


		final Statement statement = jdbcConnection.createStatement();
		statement.addBatch("delete from tb_evento");
		statement.addBatch("delete from tb_detlh_apontamento");
		statement.addBatch("delete from tb_apontamento");
		statement.addBatch("delete from tb_sub_lote where cd_lote = 202009");
		statement.addBatch("delete from tb_lote_proces where cd_lote = 202009");
		statement.addBatch("delete from tb_sub_lote where cd_lote = 3");
		statement.addBatch("delete from tb_lote_proces where cd_lote = 3");	
		statement.addBatch("update tb_sub_lote set cd_processamento = null where SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6) = 202009");
		statement.addBatch("update tb_lote_risco set fl_atulz_ld = 0 where SUBSTR(CAST(cd_lote_risco AS VARCHAR(10)), 1, 6) = 202010");
		
		
		try {
			statement.executeBatch();
		} catch (Exception e) {
			LOGGER.warn("Ignorando erros..." + e);
			e.printStackTrace();
			System.exit(0);
		}

		service.execute(ModoExecucao.TESTE);

		statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 2020020601");
		statement.addBatch("update tb_lote_risco set fl_atulz_ld = 1 where SUBSTR(CAST(cd_lote_risco AS VARCHAR(10)), 1, 6) = 202002");
		statement.addBatch("Insert into TB_LOTE_PROCES (CD_LOTE,CD_STATUS_PROCES,DT_INIC_CARGA,DT_FIM_CARGA,DT_INIC_PROCES,DT_FIM_PROCES,DT_REF,DT_ATULZ_REG) values ('202009','70',to_timestamp('20/06/20 16:29:44,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('20/06/20 16:38:44,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('15/10/18 16:39:44,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('19/10/18 16:17:33,000000000','DD/MM/RR HH24:MI:SSXFF'),null,null)");
		statement.addBatch("Insert into TB_SUB_LOTE (CD_LOTE,CD_SUBLOTE,DT_INICIO_ANALISE,DT_FIM_ANALISE,CD_PROCESSAMENTO,QT_TENTATIVA,DT_INICIO_CARGA_ANALISE,DT_FIM_CARGA_ANALISE,QT_TRANS,NM_MAQUINA) values ('202009','1',NULL,NULL,NULL,'4',to_timestamp('27/05/20 23:04:59,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('27/05/20 23:04:59,000000000','DD/MM/RR HH24:MI:SSXFF'),null,'note')");
		
		try {
			statement.executeBatch();
		} catch (Exception e) {
			LOGGER.warn("Ignorando erros..." + e);
			e.printStackTrace();
			System.exit(0);
		}

		service.execute(ModoExecucao.TESTE);
		
		
//		statement.addBatch("delete from tb_sub_lote where cd_lote = 201912");
//		statement.addBatch("delete from tb_lote_proces where cd_lote = 201912");
//		statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 2019120601");
//		
//		try {
//			statement.executeBatch();
//		} catch (Exception e) {
//			e.printStackTrace();
//			Thread.sleep(5000);
//			if (e.getMessage().contains("ORA-02292")) {
//				LOGGER.warn("Atualizando lote pois o erro indica que já existe o lote...");
//				statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 201912");''''
//				statement.addBatch("update tb_lote_proces set cd_status_proces = 70 where cd_lote = 201912");
//				statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 2020010601");
//				
//				try {
//					statement.executeBatch();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					System.exit(0);
//				}
//			}			
//		}
//
//		service.execute(ModoExecucao.TESTE);
//		
//
//		statement.addBatch("update tb_apontamento set cd_sttus_evento_atual = 5 where cd_sttus_evento_atual = 0 and rownum < 5");
//		statement.addBatch("update tb_sub_lote set cd_processamento = null "
//				+ "where SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6) = 202001 and cd_lote > 2020010601 and cd_lote <> 202001");
//		
//		try {
//			statement.executeBatch();
//		} catch (Exception e) {
//			LOGGER.warn("Ignorando erros..." + e);
//			e.printStackTrace();
//			System.exit(0);
//		}
//
//		service.execute(ModoExecucao.TESTE);
//		
//
//		statement.addBatch("update tb_sub_lote set cd_processamento = null "
//				+ "where SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6) = 202002 and cd_lote < 2020020601 and cd_lote <> 202002");
//		
//		try {
//			statement.executeBatch();
//		} catch (Exception e) {
//			LOGGER.warn("Ignorando erros..." + e);
//			e.printStackTrace();
//			System.exit(0);
//		}
//
//		service.execute(ModoExecucao.TESTE);
//
//		statement.addBatch("delete from tb_sub_lote where cd_lote = 202001");
//		statement.addBatch("delete from tb_lote_proces where cd_lote = 202001");	
//		statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 2020020601");
//		
//		try {
//			statement.executeBatch();
//		} catch (Exception e) {
//			if (e.getMessage().contains("ORA-02292")) {
//				LOGGER.warn("Atualizando lote pois o erro indica que já existe o lote...");
//				statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 202001");
//				statement.addBatch("update tb_lote_proces set cd_status_proces = 70 where cd_lote = 202001");
//				statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 2020020601");
//				
//				try {
//					statement.executeBatch();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					System.exit(0);
//				}
//			}
//		}
//
//		service.execute(ModoExecucao.TESTE);
//		
//
//		statement.addBatch("update tb_sub_lote set cd_processamento = null "
//				+ "where SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6) = 202002 and cd_lote > 2020020601 and cd_lote < 2020030101 and cd_lote <> 202002");
//		
//		try {
//			statement.executeBatch();
//		} catch (Exception e) {
//			LOGGER.warn("Ignorando erros..." + e);
//			e.printStackTrace();
//			System.exit(0);
//		}
//
//		service.execute(ModoExecucao.TESTE);
//
//		statement.addBatch("update tb_sub_lote set cd_processamento = null where CD_LOTE = 2020030101");
//		statement.addBatch("delete from tb_sub_lote where cd_lote = 202002");
//		statement.addBatch("delete from tb_lote_proces where cd_lote = 202002");	
//		
//		try {
//			statement.executeBatch();
//		} catch (Exception e) {
//			if (e.getMessage().contains("ORA-02292")) {
//				LOGGER.warn("Atualizando lote pois o erro indica que já existe o lote...");
//				statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 202002");
//				statement.addBatch("update tb_lote_proces set cd_status_proces = 70 where cd_lote = 202002");
//				statement.addBatch("update tb_sub_lote set cd_processamento = null where cd_lote = 2020030101");
//				
//				try {
//					statement.executeBatch();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					System.exit(0);
//				}
//			}
//		}
//
//		service.execute(ModoExecucao.TESTE);

		if (!jdbcConnection.isClosed()) {
			jdbcConnection.close();
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

}
