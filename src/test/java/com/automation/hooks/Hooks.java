package com.automation.hooks;

import com.automation.driver.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Hooks class for Cucumber BDD framework
 * Handles setup and teardown of WebDriver for each scenario
 * Supports parallel execution with thread-safe operations
 */
public class Hooks {
    
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private WebDriver driver;
    private Scenario scenario;
    
    /**
     * Before hook - Executed before each scenario
     * Initializes WebDriver based on browser parameter
     * @param scenario Current scenario
     */
    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        long threadId = Thread.currentThread().getId();
        String browser = System.getProperty("browser", "chrome");
        
        logger.info("===============================================");
        logger.info("Thread ID: " + threadId);
        logger.info("Scenario: " + scenario.getName());
        logger.info("Browser: " + browser);
        logger.info("===============================================");
        
        // Initialize driver
        DriverManager.initializeDriver(browser);
        this.driver = DriverManager.getDriver();
        
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(30));
        
        logger.info("WebDriver setup completed for scenario: " + scenario.getName());
    }
    
    /**
     * After hook - Executed after each scenario
     * Takes screenshot on failure and quits the WebDriver
     * @param scenario Current scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        long threadId = Thread.currentThread().getId();
        logger.info("Tearing down - Thread ID: " + threadId + " | Scenario: " + scenario.getName());
        
        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: " + scenario.getName());
            takeScreenshot(scenario);
        } else {
            logger.info("Scenario PASSED: " + scenario.getName());
        }
        
        // Quit driver
        DriverManager.quitDriver();
        logger.info("Scenario completed: " + scenario.getName());
    }
    
    /**
     * Take screenshot on test failure
     * Saves screenshot both in Cucumber report and file system
     * @param scenario Current scenario
     */
    private void takeScreenshot(Scenario scenario) {
        try {
            long threadId = Thread.currentThread().getId();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot-" + scenario.getName());
            
            // Also save to file system
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = "target/screenshots/" + threadId + "_" + scenario.getName() + "_" + timestamp + ".png";
            
            File directory = new File("target/screenshots");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            Files.write(Paths.get(fileName), screenshot);
            logger.info("Screenshot saved: " + fileName);
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
        }
    }
}
