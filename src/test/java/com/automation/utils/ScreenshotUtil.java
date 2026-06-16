package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_PATH = "target/screenshots/";

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_PATH));

            String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_PATH + fileName;

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), Paths.get(filePath));

            logger.info("Screenshot captured: " + filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}