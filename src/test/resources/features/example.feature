Feature: Example Google Search Tests
  As a user
  I want to perform searches on Google
  So that I can find information online

  Background:
    Given User is on Google homepage

  @smoke
  Scenario: User performs a basic search
    When User enters "Selenium WebDriver" in search box
    And User clicks search button
    Then Search results should be displayed
    And Search results should contain "Selenium WebDriver"

  @regression
  Scenario: User can clear search
    When User enters "Test Automation" in search box
    And User clears search box
    Then Search box should be empty