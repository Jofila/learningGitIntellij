package com.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.automation.stepDefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/json/cucumber.json",
                "junit:target/cucumber-reports/junit/cucumber.xml"
        },
        monochrome = true,
        dryRun = false,
        tags = "@smoke or @regression"
)
public class TestRunner {
}