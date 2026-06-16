package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(BasePage.class);
    private static final int EXPLICIT_WAIT_TIME = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIME));
    }

    protected WebElement waitForElement(By locator) {
        logger.info("Waiting for element: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitAndClick(By locator) {
        logger.info("Waiting and clicking element: " + locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void waitAndSendKeys(By locator, String text) {
        logger.info("Entering text '" + text + "' in element: " + locator);
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            logger.warn("Element not found or not displayed: " + locator);
            return false;
        }
    }

    protected String getElementText(By locator) {
        logger.info("Getting text from element: " + locator);
        return waitForElement(locator).getText();
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void switchToFrame(int frameIndex) {
        logger.info("Switching to frame: " + frameIndex);
        driver.switchTo().frame(frameIndex);
    }

    protected void switchToDefaultContent() {
        logger.info("Switching to default content");
        driver.switchTo().defaultContent();
    }

    protected void waitForElementInvisibility(By locator) {
        logger.info("Waiting for element invisibility: " + locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}