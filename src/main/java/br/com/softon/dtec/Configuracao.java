package br.com.softon.dtec;

import java.util.Properties;

import softonPack.util.PropertieHandle;

/**
 * @author softon
 *	Interface que implementa de forma estática as propriedades básicas de auxílio ao módulo de análise.
 */
public interface Configuracao {

    final static String DIRETORIO_CONFIGURACAO = "../conf/";

    final static String MODELO_NEURAL = "../modelos/base_balan_aa.model";

    final static String APP_ARQUIVO_CONFIGURACAO = DIRETORIO_CONFIGURACAO + "analise_properties";

    final static String APP_ARQUIVO_CONEXAO = DIRETORIO_CONFIGURACAO + "db.properties";

    final static long UM_SEGUNDO = 1 * 1000;

    final static long UM_MINUTO = 1 * 60 * 1000;

    final static long UMA_HORA = 1 * 60 * 60 * 1000;

    final static long UM_DIA = 1 * 24 * 60 * 60 * 1000;

    final Properties props_conf = PropertieHandle.loadPropertyFile(APP_ARQUIVO_CONFIGURACAO);

    final Properties props_conn = PropertieHandle.loadPropertyFile(APP_ARQUIVO_CONEXAO);

}
