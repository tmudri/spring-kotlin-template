package com.example

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
        strict = true,
        glue = [
            "com.example"
        ],
        stepNotifications = true,
        features = [
            "src/behavior-test/resources/features/factorial/factorial.feature",
            "src/behavior-test/resources/features/configuration/correlation_id.feature"
        ],
        plugin = ["pretty", "json:build/cucumber.json"]
)
class CucumberRunner
