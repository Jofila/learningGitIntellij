package com.automation.stepDefinitions;

import com.automation.driver.DriverManager;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        logger.info("Initializing WebDriver...");
        DriverManager.initializeDriver();
        logger.info("WebDriver initialized successfully");
    }

    @After
    public void tearDown() {
        logger.info("Closing WebDriver...");
        DriverManager.quitDriver();
        logger.info("WebDriver closed successfully");
    }
}