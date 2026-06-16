package com.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * TestRunner class for executing Cucumber BDD tests
 * Supports parallel execution with multiple threads
 * Generates HTML and JSON reports
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.automation.stepDefinitions", "com.automation.hooks"},
    plugin = {
        "pretty",
        "json:target/cucumber-reports/json/cucumber.json",
        "html:target/cucumber-reports/html/index.html"
    },
    monochrome = true,
    dryRun = false
)
public class TestRunner {
    /**
     * Test Runner for Cucumber BDD Framework
     * 
     * Run with Maven commands:
     * 
     * 1. Run with 4 threads (parallel):
     *    mvn test
     * 
     * 2. Run with 8 threads:
     *    mvn test -Dparallel.threads=8
     * 
     * 3. Run with 4 threads and Chrome:
     *    mvn test -Dbrowser=chrome -Dparallel.threads=4
     * 
     * 4. Run with 4 threads and Firefox:
     *    mvn test -Dbrowser=firefox -Dparallel.threads=4
     * 
     * 5. Run with 4 threads in headless mode:
     *    mvn test -Dparallel.threads=4 -Dheadless=true
     * 
     * 6. Run specific feature file with parallel execution:
     *    mvn test -Dcucumber.features="src/test/resources/features/GoogleSearch.feature" -Dparallel.threads=4
     */
}
