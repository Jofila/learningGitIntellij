package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;

/**
 * DriverManager is responsible for managing WebDriver instances
 * in a thread-safe manner using ThreadLocal for parallel execution support.
 * Supports Chrome, Firefox, Edge, and Safari browsers.
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    
    // ThreadLocal for thread-safe driver management (critical for parallel execution)
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Initialize WebDriver based on browser type
     * @param browserName Name of the browser (chrome, firefox, edge, safari)
     */
    public static void initializeDriver(String browserName) {
        String browser = System.getProperty("browser", browserName).toLowerCase();
        long threadId = Thread.currentThread().getId();
        logger.info("Initializing " + browser + " driver for thread: " + threadId);
        
        switch (browser) {
            case "chrome":
                initializeChromeDriver();
                break;
            case "firefox":
                initializeFirefoxDriver();
                break;
            case "edge":
                initializeEdgeDriver();
                break;
            case "safari":
                initializeSafariDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }
    
    /**
     * Initialize Chrome WebDriver with options
     */
    private static void initializeChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        
        // Disable GPU acceleration for parallel execution
        options.addArguments("--disable-gpu");
        
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        driver.set(new ChromeDriver(options));
        logger.info("Chrome driver initialized for thread: " + Thread.currentThread().getId());
    }
    
    /**
     * Initialize Firefox WebDriver with options
     */
    private static void initializeFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        driver.set(new FirefoxDriver(options));
        logger.info("Firefox driver initialized for thread: " + Thread.currentThread().getId());
    }
    
    /**
     * Initialize Edge WebDriver with options
     */
    private static void initializeEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        driver.set(new EdgeDriver(options));
        logger.info("Edge driver initialized for thread: " + Thread.currentThread().getId());
    }
    
    /**
     * Initialize Safari WebDriver
     */
    private static void initializeSafariDriver() {
        driver.set(new SafariDriver());
        logger.info("Safari driver initialized for thread: " + Thread.currentThread().getId());
    }
    
    /**
     * Get WebDriver instance for current thread
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new RuntimeException("WebDriver is not initialized for thread: " + Thread.currentThread().getId());
        }
        return webDriver;
    }
    
    /**
     * Quit WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        long threadId = Thread.currentThread().getId();
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
            logger.info("Driver quit for thread: " + threadId);
        }
    }
}
