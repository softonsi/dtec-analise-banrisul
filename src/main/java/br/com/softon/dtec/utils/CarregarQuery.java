package br.com.softon.dtec.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.softon.dtec.Configuracao;

public class CarregarQuery {

    private final static Logger log = LoggerFactory.getLogger(CarregarQuery.class);

    private static final String PACOTE_DAO = "br/com/softon/dtec/dao/";

    private static final String PACOTE_DAO_SQLSERVER = "br/com/softon/dtec/daoSQLServer/";

    private CarregarQuery() {
    }

    private static Map<String, String> cache = new ConcurrentHashMap<String, String>();

    public enum Origem {
    	SIMULA_CONTROLE, SUBLOTE, APONTAMENTOS, REGRAS, TRANSACAO, LISTA_AUXILIAR, PERFIL, TESTE, CENARIOS, VALIDACAO, MEDIA_OCUPACAO, MEDIA_RAMO_ATIVIDADE, CADASTRO_CLIENTE,
        RENDA_CLIENTE, AGENCIA, PAIS, PERFIL_MES_CALENDARIO, NATUREZA_DECLARADA, PRODUTO, PPE, GOV, CADEIA_SOCIETARIA, PERFIL_INFORMACAO, CONTRATO,  ELEMENTOS_COMUNS_CONTA,
        TESTES_CONCLUSAO_ANALISE, TRANSMISSAO_ORDEM, VALIDACAO_BRB_CARTOES, VALIDACAO_TOYOTA, VALIDACAO_PAN, VALIDACAO_CATERPILLAR, VALIDACAO_BRB, VARIAVEL_PERFIL, 
        VALIDACAO_CARREFOUR, VALIDACAO_RENNER, VALIDACAO_SUPERDIGITAL, VALIDACAO_MERCANTIL, VALIDACAO_QUICARD;

        public String getPath() {
            return toString().toLowerCase() + "/";
        }
    }

    public static String carregarQuery(final Origem origem, final DatabaseMetaData databaseMetaData, final String nome, String... args) throws SQLException {
        String nomeCompleto = null;
        try {
            nomeCompleto = Configuracao.props_conn.getProperty("banco").equalsIgnoreCase("ORACLE") ? 
            		PACOTE_DAO + origem.getPath() + databaseMetaData.getDatabaseProductName().toLowerCase() + "_" + nome : 
            		PACOTE_DAO_SQLSERVER + origem.getPath() + databaseMetaData.getDatabaseProductName().toLowerCase() + "_" + nome;
        } catch (SQLException e) {
            log.error("Houve um erro ao carregar query a ser executada...");
            throw e;
        }

        if (!cache.containsKey(nomeCompleto)) {
            try {
                final URL resource = Thread.currentThread().getContextClassLoader().getResource(nomeCompleto);
                if (resource == null) {
                    return carregarQuery(origem, nome, args);
                }

                final InputStream content = (InputStream) resource.getContent();
                if (content == null) {
                    throw new RuntimeException("Conteúdo do resource '" + nomeCompleto + "' está nulo.");
                }
                final String valor = IOUtils.toString(content).replace("\n", " ");
                if (args != null && args.length > 0) {
                    cache.put(nomeCompleto, String.format(valor, (Object[])args));
                } else {
                    cache.put(nomeCompleto, valor);
                }
            } catch (Exception ex) {
//                log.error("Houve um erro ao carregar query a ser executada...");
                throw new RuntimeException(ex);
            }
        }
        return cache.get(nomeCompleto);

    }

    public static String carregarQuery(final Origem origem, final String nome, String... args) {
        final String nomeCompleto = Configuracao.props_conn.getProperty("banco").equalsIgnoreCase("ORACLE") ? PACOTE_DAO + origem.getPath() + nome : PACOTE_DAO_SQLSERVER + origem.getPath() + nome;

        if (!cache.containsKey(nomeCompleto)) {
            try {
                final URL resource = Thread.currentThread().getContextClassLoader().getResource(nomeCompleto);
                if (resource == null) {
                    throw new FileNotFoundException("Resource '" + nomeCompleto + "' não encontrado.");
                }

                final InputStream content = (InputStream) resource.getContent();
                if (content == null) {
                    throw new RuntimeException("Conteúdo do resource '" + nomeCompleto + "' está nulo.");
                }
                final String valor = IOUtils.toString(content).replace("\n", " ");
                if (args != null && args.length > 0) {
                    cache.put(nomeCompleto, String.format(valor, (Object[])args));
                } else {
                    cache.put(nomeCompleto, valor);
                }
            } catch (Exception ex) {
//                log.error("Houve um erro ao carregar query a ser executada...");
                throw new RuntimeException(ex);
            }
        }
        return cache.get(nomeCompleto);
    }

}
