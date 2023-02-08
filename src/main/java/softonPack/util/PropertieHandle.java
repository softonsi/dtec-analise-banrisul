package softonPack.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mario
 */
public class PropertieHandle {

    private final static Logger log = LoggerFactory.getLogger(PropertieHandle.class);
    Properties props = new Properties();
    static Properties propsStatic;
    
    private static Boolean teste = false;
    
    public static void setTeste(Boolean teste) {
    	PropertieHandle.teste = teste;
    }

    public PropertieHandle(String pathAndFileName) {
        try {
            this.props.load(new FileInputStream(new File(pathAndFileName)));
        } catch (IOException e) {
            log.error("Arquivo de propiedades: " + pathAndFileName + " não encontrado. " + e);
        }

    }

    public static void setStatic(String pathAndFileName) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File(pathAndFileName)));
        } catch (IOException e) {
            log.error("Arquivo de propiedades: " + pathAndFileName + " não encontrado. " + e);
        }
        propsStatic = prop;
    }

    public static void setStaticStream(InputStream pathAndFileName) {
        Properties prop = new Properties();
        try {
            prop.load(pathAndFileName);
        } catch (IOException e) {
            log.error("Arquivo de propiedades: " + pathAndFileName + " não encontrado. " + e);
        }
        propsStatic = prop;
    }

    public static void setStatic(File file) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(file));
        } catch (IOException e) {
            log.error("Arquivo de propiedades: " + file + " não encontrado. " + e);
        }
        propsStatic = prop;
    }

    /**
     * @param fileName
     * @return Properties carregado pronto para dar getProperty()
     * @autor Mario Caseiro
     * <p/>
     * Retorna um java.util.properties carregado com o arquivo informado no parâmetro.
     * O parametro deve ser um path absoluto.
     */
    public static Properties loadPropertyFile(String pathAndFileName) {

        Properties props = new Properties();
        
        if(teste) {
        	pathAndFileName = pathAndFileName.replace("../", "");
        }

        try {
            props.load(new FileInputStream(new File(pathAndFileName)));
        } catch (IOException e) {
            log.error("Arquivo de propriedades: " + pathAndFileName + " não encontrado. " + e);
        }

        return props;
    }

    public static Properties loadPropertyFileStream(String pathAndFileName) {
        Properties props = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream(pathAndFileName);

        try {
            if (is == null) {
                throw new IOException();
            }
            props.load(is);
        } catch (Exception e) {
            log.error("Arquivo de propiedades: " + pathAndFileName + " não encontrado. " + e);
        }

        return props;
    }

    public static void savePropertyFile(String pathAndFileName, String key, String value) {
        Properties props = new Properties();

        try {
            props.load(new FileInputStream(new File(pathAndFileName)));
            props.put(key, value);
            FileOutputStream out = new FileOutputStream(pathAndFileName);
            props.store(out, "Alterado em " + new Date().toString());
            out.close();

        } catch (IOException e) {
            log.error("Arquivo de propiedades: " + pathAndFileName + " não encontrado. " + e);
        }
    }

    /**
     * @param fileName
     * @return Properties carregado pronto para dar getProperty()
     * @autor Mario Caseiro
     * <p/>
     * Retorna um java.util.properties carregado com o arquivo informado no parâmetro.
     * O parâmetro é relativo ao resource da aplicação
     */
    public static Properties loadPropertyFromResource(String pathAndFileName) {
        InputStream file = null;
        try {
            file = PropertieHandle.class.getResourceAsStream(pathAndFileName);
            if (file == null) {
                throw new IOException("PropertieHandle.loadPropertyFromResource() error: " +
                        pathAndFileName + " não encontrado");
            }
        } catch (IOException e) {
            log.error("Arquivo de propiedades: " + pathAndFileName + " não encontrado. " + e);
        }

        Properties props = new Properties();

        try {
            props.load(file);
        } catch (IOException e) {
            log.error("Arquivo de propiedades: " + pathAndFileName + " não encontrado. " + e);
        }

        return props;
    }

    public static String getPropertieStatic(String propName) {
        return propsStatic.getProperty(propName);
    }

    public String getPropertie(String propName) {
        String propertie;
        propertie = props.getProperty(propName);

        return propertie;
    }

    public Boolean hasPropertie(String propName) {
        if (this.props.containsKey(propName)) {
            return true;
        }

        return false;
    }

}
