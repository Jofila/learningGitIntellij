package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static WebDriver driver;
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void initializeDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        logger.info("Initializing WebDriver for browser: " + browser);

        switch (browser) {
            case "chrome":
                driver = initializeChromeDriver(headless);
                break;
            case "firefox":
                driver = initializeFirefoxDriver(headless);
                break;
            case "edge":
                driver = initializeEdgeDriver(headless);
                break;
            default:
                logger.warn("Browser not found, defaulting to Chrome");
                driver = initializeChromeDriver(headless);
        }

        setImplicitWait();
        setPageLoadTimeout();
        driverThreadLocal.set(driver);
    }

    private static WebDriver initializeChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless");
            logger.info("Running Chrome in headless mode");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    private static WebDriver initializeFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
            logger.info("Running Firefox in headless mode");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver initializeEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless");
            logger.info("Running Edge in headless mode");
        }

        return new EdgeDriver(options);
    }

    private static void setImplicitWait() {
        int implicitWait = Integer.parseInt(System.getProperty("implicit.wait", "5"));
        driver.manage().timeouts().implicitlyWait(
                java.time.Duration.ofSeconds(implicitWait)
        );
        logger.info("Implicit wait set to: " + implicitWait + " seconds");
    }

    private static void setPageLoadTimeout() {
        int pageLoadTimeout = Integer.parseInt(System.getProperty("page.load.timeout", "15"));
        driver.manage().timeouts().pageLoadTimeout(
                java.time.Duration.ofSeconds(pageLoadTimeout)
        );
        logger.info("Page load timeout set to: " + pageLoadTimeout + " seconds");
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            logger.warn("WebDriver not initialized. Initializing now...");
            initializeDriver();
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
            logger.info("WebDriver quit successfully");
        }
    }
}