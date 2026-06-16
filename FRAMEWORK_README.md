# Web Automation Framework

A comprehensive test automation framework for web-based applications using Selenium, Java, BDD (Cucumber), and Maven.

## Features

- **Selenium WebDriver 4.x** - Latest WebDriver API for browser automation
- **Cucumber BDD** - Behavior-Driven Development with Gherkin feature files
- **Page Object Model (POM)** - Organized page object classes for maintainability
- **Maven** - Build automation and dependency management
- **Log4j 2** - Comprehensive logging
- **WebDriverManager** - Automatic driver management (no manual driver downloads needed)
- **Thread-safe Driver** - Support for parallel test execution
- **Multi-browser Support** - Chrome, Firefox, and Edge
- **Headless Mode** - Run tests without opening browser UI
- **Screenshot on Failure** - Automatic screenshots on test failures

## Project Structure

```
web-automation-framework/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com/automation/
│   │   │       ├── driver/
│   │   │       │   └── DriverManager.java
│   │   │       ├── pages/
│   │   │       │   ├── BasePage.java
│   │   │       │   └── GoogleHomePage.java
│   │   │       ├── stepDefinitions/
│   │   │       │   ├── GoogleSearchSteps.java
│   │   │       │   └── Hooks.java
│   │   │       ├── runner/
│   │   │       │   └── TestRunner.java
│   │   │       └── utils/
│   │   │           └── ScreenshotUtil.java
│   │   └── resources/
│   │       ├── features/
│   │       │   └── example.feature
│   │       ├── config/
│   │       │   └── config.properties
│   │       └── log4j2.xml
└── pom.xml
```

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Git

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Jofila/learningGitIntellij.git
   cd learningGitIntellij
   git checkout test-automation-framework
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

## Running Tests

### Run all tests
```bash
mvn test
```

### Run specific test tags
```bash
mvn test -Dcucumber.options="--tags @smoke"
mvn test -Dcucumber.options="--tags @regression"
```

### Run with specific browser
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Run in headless mode
```bash
mvn test -Dheadless=true
```

## Configuration

Edit `src/test/resources/config/config.properties` to configure:

- **browser** - Chrome, Firefox, or Edge (default: chrome)
- **headless** - Run in headless mode (default: false)
- **app.url** - Base URL of the application under test
- **explicit.wait** - Explicit wait timeout in seconds (default: 10)
- **implicit.wait** - Implicit wait timeout in seconds (default: 5)
- **screenshot.on.failure** - Take screenshot on test failure (default: true)

## Writing Tests

### 1. Create Feature File

Create a new `.feature` file in `src/test/resources/features/`:

```gherkin
Feature: Example Feature
  As a user
  I want to perform an action
  So that I can achieve a goal

  Scenario: Example Scenario
    Given User is on the application
    When User performs an action
    Then Expected result should be displayed
```

### 2. Create Page Object

Create a new page object class extending `BasePage`:

```java
public class ExamplePage extends BasePage {
    private static final By ELEMENT_LOCATOR = By.id("element-id");
    
    public ExamplePage(WebDriver driver) {
        super(driver);
    }
    
    public void performAction() {
        waitAndClick(ELEMENT_LOCATOR);
    }
}
```

### 3. Create Step Definition

Create step definitions in `src/test/java/com/automation/stepDefinitions/`:

```java
public class ExampleSteps {
    private WebDriver driver = DriverManager.getDriver();
    private ExamplePage examplePage = new ExamplePage(driver);
    
    @Given("User is on the application")
    public void userIsOnApplication() {
        driver.get("http://example.com");
    }
    
    @When("User performs an action")
    public void userPerformsAction() {
        examplePage.performAction();
    }
    
    @Then("Expected result should be displayed")
    public void verifyResult() {
        Assert.assertTrue("Result not displayed", examplePage.isResultDisplayed());
    }
}
```

## Test Reports

After running tests, HTML reports are generated at:
- `target/cucumber-reports/cucumber.html` - Cucumber HTML report
- `target/cucumber-reports/junit/cucumber.xml` - JUnit XML report

## Logging

Logs are written to:
- Console output
- `target/logs/automation.log` - Log file

## Best Practices

1. **Use Page Object Model** - Keep page elements and actions in separate classes
2. **Use BasePage** - Extend BasePage for common WebDriver interactions
3. **Explicit Waits** - Use explicit waits instead of Thread.sleep()
4. **Descriptive Selectors** - Use meaningful XPath or CSS selectors
5. **Error Handling** - Use try-catch blocks for expected failures
6. **Logging** - Log important steps and data for debugging
7. **DRY Principle** - Don't Repeat Yourself - create reusable methods
8. **Tag Scenarios** - Use tags (@smoke, @regression, etc.) for test categorization

## Troubleshooting

### Issue: Driver not found
**Solution**: The project uses WebDriverManager which automatically downloads drivers. Ensure internet connection is available.

### Issue: Tests running slowly
**Solution**: Increase implicit/explicit wait times in `config.properties` or run in headless mode.

### Issue: Locators not working
**Solution**: Use browser developer tools (F12) to inspect elements and verify locators.

## Contributing

1. Create a feature branch: `git checkout -b feature/new-tests`
2. Make your changes
3. Commit: `git commit -am 'Add new tests'`
4. Push: `git push origin feature/new-tests`
5. Create a Pull Request

## License

MIT License - See LICENSE file for details

## Author

Jofila

## Support

For issues and questions, please open an issue on GitHub.