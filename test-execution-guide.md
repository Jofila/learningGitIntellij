# Test Automation Framework - Execution Guide

## Quick Start Commands

### 1. Run All Tests (Chrome, 4 Parallel Threads)
```bash
mvn clean test
```

### 2. Cross-Browser Testing

#### Chrome (Default)
```bash
mvn clean test -Dbrowser=chrome -Dparallel.threads=4
```

#### Firefox
```bash
mvn clean test -Dbrowser=firefox -Dparallel.threads=4
```

#### Edge
```bash
mvn clean test -Dbrowser=edge -Dparallel.threads=4
```

### 3. Parallel Execution with Different Thread Counts

#### Sequential (1 Thread)
```bash
mvn clean test -Dparallel.threads=1 -Dbrowser=chrome
```

#### Parallel (4 Threads)
```bash
mvn clean test -Dparallel.threads=4 -Dbrowser=chrome
```

#### High Parallelism (8 Threads)
```bash
mvn clean test -Dparallel.threads=8 -Dbrowser=chrome
```

### 4. Headless Mode (for CI/CD)
```bash
mvn clean test -Dheadless=true -Dparallel.threads=4 -Dbrowser=chrome
```

### 5. Combined Advanced Commands

#### Firefox + Headless + 4 Threads
```bash
mvn clean test -Dbrowser=firefox -Dheadless=true -Dparallel.threads=4
```

#### Edge + 8 Threads + Headless
```bash
mvn clean test -Dbrowser=edge -Dheadless=true -Dparallel.threads=8
```

#### Specific Feature File
```bash
mvn clean test -Dcucumber.features="src/test/resources/features/GoogleSearch.feature" -Dparallel.threads=4
```

## Performance Comparison

Run this sequence to compare performance:

```bash
# Sequential (Baseline)
echo "Sequential Test Run (1 thread)" && time mvn clean test -Dparallel.threads=1 -Dheadless=true

# Parallel 4 threads
echo "Parallel Test Run (4 threads)" && time mvn clean test -Dparallel.threads=4 -Dheadless=true

# Parallel 8 threads
echo "Parallel Test Run (8 threads)" && time mvn clean test -Dparallel.threads=8 -Dheadless=true
```

## Test Reports

After test execution, access the reports:

### HTML Report
```
target/cucumber-reports/html/index.html
```

### JSON Report (for CI/CD integration)
```
target/cucumber-reports/json/cucumber.json
```

### Screenshots (on test failure)
```
target/screenshots/
```

## Troubleshooting

### 1. Chrome Driver Issues
```bash
# Clean and rebuild
mvn clean install

# Run with verbose output
mvn clean test -Dbrowser=chrome -X
```

### 2. Firefox Driver Issues
```bash
mvn clean test -Dbrowser=firefox -X
```

### 3. Check Java Version
```bash
java -version
# Should be Java 11 or higher
```

### 4. View Maven Dependencies
```bash
mvn dependency:tree
```

## Test Structure

```
src/
├── main/java/com/automation/
│   ├── driver/
│   │   └── DriverManager.java          # Thread-safe driver management
│   └── pages/
│       └── GoogleHomePage.java         # Page Object Model
├── test/
│   ├── java/com/automation/
│   │   ├── runner/
│   │   │   └── TestRunner.java         # Cucumber runner
│   │   ├── hooks/
│   │   │   └── Hooks.java              # Setup/teardown
│   │   └── stepDefinitions/
│   │       └── GoogleSearchSteps.java   # Step implementations
│   └── resources/
│       ├── features/
│       │   └── GoogleSearch.feature    # BDD scenarios
│       └── config.properties           # Configuration
```

## Maven Profile for Quick Testing

Add to pom.xml for quick commands:

```xml
<profiles>
    <profile>
        <id>chrome-4</id>
        <properties>
            <browser>chrome</browser>
            <parallel.threads>4</parallel.threads>
        </properties>
    </profile>
    <profile>
        <id>firefox-8</id>
        <properties>
            <browser>firefox</browser>
            <parallel.threads>8</parallel.threads>
        </properties>
    </profile>
    <profile>
        <id>headless-4</id>
        <properties>
            <browser>chrome</browser>
            <parallel.threads>4</parallel.threads>
            <headless>true</headless>
        </properties>
    </profile>
</profiles>
```

Then run:
```bash
mvn clean test -Pchrome-4
mvn clean test -Pfirefox-8
mvn clean test -Pheadless-4
```

## Expected Results

### Successful Test Execution
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.automation.runner.TestRunner
...
[INFO] Tests run: X, Failures: 0, Errors: 0
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS
```

### Performance Metrics (Example)
- Sequential (1 thread): ~10 minutes for 10 tests
- Parallel (4 threads): ~2.5 minutes for 10 tests (75% reduction)
- Parallel (8 threads): ~1.5 minutes for 10 tests (85% reduction)

## Support & Documentation

- **Selenium**: https://www.selenium.dev/documentation/
- **Cucumber**: https://cucumber.io/docs/cucumber/
- **WebDriverManager**: https://github.com/bonigarcia/webdrivermanager
- **Maven**: https://maven.apache.org/guides/

## Next Steps

1. ✅ Code is ready to execute
2. 📝 Run one of the Maven commands above
3. 📊 Check reports in `target/cucumber-reports/html/`
4. 🔄 Iterate and enhance tests
5. 🚀 Integrate with CI/CD pipeline
