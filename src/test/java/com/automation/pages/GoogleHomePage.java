package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage extends BasePage {

    private static final By SEARCH_BOX = By.name("q");
    private static final By SEARCH_BUTTON = By.xpath("//button[contains(@aria-label, 'Google Search')]");
    private static final By SEARCH_RESULTS = By.xpath("//div[@id='search']");
    private static final By RESULTS_CONTAINER = By.xpath("//div[@data-sokoban-container]");
    private static final By COOKIE_ACCEPT = By.xpath("//button[contains(text(), 'Accept all')]");

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies() {
        if (isElementPresent(COOKIE_ACCEPT)) {
            waitAndClick(COOKIE_ACCEPT);
        }
    }

    public void enterSearchText(String searchText) {
        waitAndSendKeys(SEARCH_BOX, searchText);
    }

    public void clickSearchButton() {
        WebElement searchBox = waitForElement(SEARCH_BOX);
        searchBox.submit();
    }

    public boolean isSearchResultsDisplayed() {
        return isElementDisplayed(RESULTS_CONTAINER);
    }

    public boolean searchResultsContainText(String text) {
        String pageSource = driver.getPageSource();
        return pageSource.contains(text);
    }

    public void clearSearchBox() {
        WebElement searchBox = waitForElement(SEARCH_BOX);
        searchBox.clear();
    }

    public boolean isSearchBoxEmpty() {
        WebElement searchBox = waitForElement(SEARCH_BOX);
        return searchBox.getAttribute("value").isEmpty();
    }
}