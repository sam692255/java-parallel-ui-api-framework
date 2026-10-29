package com.framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(boolean isRemote, String gridUrl) {
        if (driverThreadLocal.get() == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox");
            try {
                if (isRemote) {
                    driverThreadLocal.set(new RemoteWebDriver(new URI(gridUrl).toURL(), options));
                } else {
                    driverThreadLocal.set(new ChromeDriver(options));
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Selenium Grid URL: " + gridUrl, e);
            }
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}