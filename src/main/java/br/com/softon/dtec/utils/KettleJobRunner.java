package br.com.softon.dtec.utils;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;

public class KettleJobRunner implements Configuracao {
	
	private final static Logger logger = LoggerFactory
			.getLogger(KettleJobRunner.class);
	
	private static final String KETTLE_KJB = props_conf.getProperty("kettle_file_ponto_corte");

	public KettleJobRunner() {
	}
	
	public static String runJob() throws Exception {

		try {
			if (!KettleEnvironment.isInitialized()) {
				KettleEnvironment.init(false);
			}
			
			JobMeta jobMeta = new JobMeta(KETTLE_KJB, null);

			Job job = new Job(null, jobMeta);

			job.setLogLevel(org.pentaho.di.core.logging.LogLevel.BASIC);

			job.start();

			job.waitUntilFinished();

			Result result = job.getResult();

			String outcome = "\n Resultado: " + (result.getResult() ? "sucesso" : "falha") + " com "
					+ result.getNrErrors() + " erros\n";
			
			logger.info(outcome);
			
			if(result.getNrErrors() > 0)
				throw new RuntimeException("Ocorreram erros ao processar o job do kettle");

		} catch (Exception e) {
			throw e;
		}
		
		return null;

	}
	
}