package br.com.softon.util;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class RunWebDriver {

    private static WebDriver webDriver;

    public static WebDriver getWebDriver() {
    	webDriver.manage().window().setPosition(new Point(2000, 0));
        return webDriver;
    }

    static void setWebDriver(WebDriver webDriver) {
    	RunWebDriver.webDriver = webDriver;
    }

}
