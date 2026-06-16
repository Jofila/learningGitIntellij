package com.automation.stepDefinitions;

import com.automation.driver.DriverManager;
import com.automation.pages.GoogleHomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class GoogleSearchSteps {

    private WebDriver driver;
    private GoogleHomePage googleHomePage;

    public GoogleSearchSteps() {
        this.driver = DriverManager.getDriver();
        this.googleHomePage = new GoogleHomePage(driver);
    }

    @Given("User is on Google homepage")
    public void userIsOnGoogleHomepage() {
        String appUrl = System.getProperty("app.url", "https://www.google.com");
        driver.get(appUrl);
        googleHomePage.acceptCookies();
    }

    @When("User enters {string} in search box")
    public void userEntersInSearchBox(String searchText) {
        googleHomePage.enterSearchText(searchText);
    }

    @And("User clicks search button")
    public void userClicksSearchButton() {
        googleHomePage.clickSearchButton();
    }

    @Then("Search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        Assert.assertTrue("Search results not displayed", googleHomePage.isSearchResultsDisplayed());
    }

    @Then("Search results should contain {string}")
    public void searchResultsShouldContain(String expectedText) {
        Assert.assertTrue("Search results do not contain expected text",
                googleHomePage.searchResultsContainText(expectedText));
    }

    @And("User clears search box")
    public void userClearsSearchBox() {
        googleHomePage.clearSearchBox();
    }

    @Then("Search box should be empty")
    public void searchBoxShouldBeEmpty() {
        Assert.assertTrue("Search box is not empty", googleHomePage.isSearchBoxEmpty());
    }
}