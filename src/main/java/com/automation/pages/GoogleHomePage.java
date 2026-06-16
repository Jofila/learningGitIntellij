package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object Model for Google Search Homepage
 * Handles all interactions with the Google search page
 */
public class GoogleHomePage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators using @FindBy annotation
    @FindBy(name = "q")
    private WebElement searchBox;
    
    @FindBy(name = "btnK")
    private WebElement searchButton;
    
    @FindBy(id = "result-stats")
    private WebElement searchResults;
    
    // Alternative locators for search results
    private By searchResultsLocator = By.id("search");
    private By cookiesButtonLocator = By.id("L2AGLb");
    
    /**
     * Constructor to initialize PageFactory and WebDriverWait
     * @param driver WebDriver instance
     */
    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Accept cookies if cookie banner is present
     * Handles exception if cookie banner is not available
     */
    public void acceptCookies() {
        try {
            WebElement cookiesButton = wait.until(ExpectedConditions.presenceOfElementLocated(cookiesButtonLocator));
            if (cookiesButton.isDisplayed()) {
                cookiesButton.click();
                System.out.println("Cookies accepted successfully");
            }
        } catch (Exception e) {
            // Cookie banner might not appear or already accepted
            System.out.println("Cookie banner not found or already accepted");
        }
    }
    
    /**
     * Enter search text in the search box
     * @param searchText Text to search
     */
    public void enterSearchText(String searchText) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            searchBox.clear();
            searchBox.sendKeys(searchText);
            System.out.println("Entered search text: " + searchText);
        } catch (Exception e) {
            System.err.println("Failed to enter search text: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click the search button
     */
    public void clickSearchButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            searchButton.click();
            System.out.println("Search button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click search button: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Check if search results are displayed
     * @return true if search results are visible, false otherwise
     */
    public boolean isSearchResultsDisplayed() {
        try {
            WebElement results = wait.until(ExpectedConditions.presenceOfElementLocated(searchResultsLocator));
            boolean isDisplayed = results.isDisplayed();
            System.out.println("Search results displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("Search results not found: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if search results contain specific text
     * @param expectedText Text to search for in results
     * @return true if text is found in results, false otherwise
     */
    public boolean searchResultsContainText(String expectedText) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(searchResultsLocator));
            String pageText = driver.getPageSource();
            boolean contains = pageText.contains(expectedText);
            System.out.println("Search results contain '" + expectedText + "': " + contains);
            return contains;
        } catch (Exception e) {
            System.err.println("Failed to check search results: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Clear the search box
     */
    public void clearSearchBox() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            searchBox.clear();
            System.out.println("Search box cleared");
        } catch (Exception e) {
            System.err.println("Failed to clear search box: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Check if search box is empty
     * @return true if search box is empty, false otherwise
     */
    public boolean isSearchBoxEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            String value = searchBox.getAttribute("value");
            boolean isEmpty = value == null || value.isEmpty();
            System.out.println("Search box is empty: " + isEmpty);
            return isEmpty;
        } catch (Exception e) {
            System.err.println("Failed to check if search box is empty: " + e.getMessage());
            return false;
        }
    }
}
