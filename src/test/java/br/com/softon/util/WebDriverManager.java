package br.com.softon.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {

    public static void initWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/softon/JAVA/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        RunWebDriver.setWebDriver(driver);
        
        // Ajustar o banco de dados para a base que precisamos...
//        try {
//			Process processo = Runtime.getRuntime().exec("/home/softon/JAVA/workspace_dtec_ld_v3_analise/dtec-ld-v3-analise-banrisul/bin/script.sh");
//			processo.waitFor();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    public static void shutDownDriver() {
    	RunWebDriver.getWebDriver().quit();
    }

}
