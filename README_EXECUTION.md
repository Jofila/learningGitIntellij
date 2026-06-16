# Cross-Browser Testing & Parallel Execution Guide

## Framework Features

✅ **Cross-Browser Support** - Chrome, Firefox, Edge, Safari  
✅ **Parallel Execution** - Run tests concurrently across multiple threads  
✅ **Thread-Safe** - ThreadLocal WebDriver management  
✅ **Headless Mode** - CI/CD integration ready  
✅ **Automatic Screenshots** - On test failure  
✅ **Detailed Logging** - Thread ID tracking for debugging  
✅ **HTML Reports** - Cucumber reports with embedded screenshots  

## Maven Commands

### Basic Execution
```bash
# Run all tests with default settings (Chrome, 4 threads)
mvn test

# Run specific feature file
mvn test -Dcucumber.features="src/test/resources/features/GoogleSearch.feature"
```

### Browser Selection
```bash
# Run with Firefox
mvn test -Dbrowser=firefox

# Run with Edge
mvn test -Dbrowser=edge

# Run with Safari
mvn test -Dbrowser=safari
```

### Parallel Execution
```bash
# Run with 8 threads
mvn test -Dparallel.threads=8

# Run with 2 threads
mvn test -Dparallel.threads=2
```

### Combined Commands
```bash
# Run with Firefox in 8 parallel threads
mvn test -Dbrowser=firefox -Dparallel.threads=8

# Run with Chrome in headless mode, 4 threads
mvn test -Dbrowser=chrome -Dheadless=true -Dparallel.threads=4

# Run with Edge in headless mode, 8 threads
mvn test -Dbrowser=edge -Dheadless=true -Dparallel.threads=8
```

## Project Structure

```
src/
├── main/java/com/automation/
│   ├── driver/
│   │   └── DriverManager.java          # Thread-safe driver management
│   └── pages/
│       └── GoogleHomePage.java         # Page Object Model
│
└── test/
    ├── java/com/automation/
    │   ├── runner/
    │   │   └── TestRunner.java         # Cucumber test runner
    │   ├── hooks/
    │   │   └── Hooks.java              # Setup/teardown hooks
    │   ├── stepDefinitions/
    │   │   └── GoogleSearchSteps.java  # Step implementations
    │   └── pages/
    │
    └── resources/
        ├── features/
        │   └── GoogleSearch.feature    # BDD scenarios
        └── config.properties           # Configuration
```

## Key Components

### 1. DriverManager.java
- Manages WebDriver instances using ThreadLocal
- Supports Chrome, Firefox, Edge, Safari
- Automatic driver download via WebDriverManager
- Configurable headless mode

### 2. Hooks.java
- @Before: Initializes driver for each scenario
- @After: Quits driver and takes screenshots on failure
- Thread-safe operations with logging

### 3. TestRunner.java
- Cucumber test runner configuration
- Feature file and glue path setup
- Report generation plugins

## Performance Benefits

**Parallel Execution (4 threads):**
- 10 tests in ~2.5 minutes (vs 10 minutes sequentially)
- **60-75% time reduction**

**Headless Mode:**
- No browser UI rendering
- Additional 20-30% speed improvement
- Ideal for CI/CD pipelines

## Debugging Tips

1. **Check Thread ID in Logs:**
   - Each log line shows thread ID for easier debugging
   - Screenshots saved with thread ID prefix

2. **View Reports:**
   - HTML Report: `target/cucumber-reports/html/index.html`
   - Screenshots: `target/screenshots/`

3. **Enable Logging:**
   - Check `src/main/resources/log4j2.xml` for configuration
   - Adjust log levels as needed

## CI/CD Integration Examples

### GitHub Actions
```yaml
- name: Run Tests
  run: mvn test -Dbrowser=chrome -Dheadless=true -Dparallel.threads=4
```

### Jenkins
```groovy
stage('Test') {
    steps {
        sh 'mvn test -Dbrowser=chrome -Dheadless=true -Dparallel.threads=8'
    }
}
```

## Troubleshooting

**Issue: Tests fail in parallel mode but pass sequentially**
- Ensure each test is independent
- Check for shared state or hardcoded waits
- Review DriverManager for thread safety

**Issue: Browser doesn't launch**
- Verify WebDriverManager has internet connection
- Check browser is installed
- Review logs for specific errors

**Issue: Screenshots not captured**
- Verify `target/screenshots/` directory exists
- Check write permissions
- Review test failure logs

## Best Practices

1. **Use Page Object Model** for maintainability
2. **Avoid hard-coded waits** - use Selenium waits
3. **Keep tests independent** for parallel execution
4. **Use descriptive feature names** for reporting
5. **Implement proper error handling** in step definitions
6. **Monitor thread usage** to avoid system overload

## Support

For issues or questions, refer to:
- Selenium Documentation: https://www.selenium.dev/
- Cucumber Documentation: https://cucumber.io/
- WebDriverManager: https://github.com/bonigarcia/webdrivermanager
