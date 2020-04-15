#Kotlin with Spring template

This project is create to serve as a template for micro services based on Kotlin and Spring Boot.

Features:
 * Basic spring boot setup
 * Tests split by category
   * Unit
   * Component
   * Integration
   * Behavior
 * Example REST service
    * REST controller
    * Exception Handler
    * Backing service
    * TODO: Database connection (PostgreSQL)
 * Middleware
    * Correlation ID management
    * TODO: Prometheus metrics
    * TODO: Kubernetes health and ready endpoints
 * Code formatting with linter ([ktlint](https://github.com/jlleitschuh/ktlint-gradle))  
 * Gradle test output logging during build
 * Logging with logback (TODO: log in json format)
 * TODO: Docker build file
 * TODO: Kubernetes deployment configuration
   

## ktlint 
https://github.com/jlleitschuh/ktlint-gradle

## Gradle test logger
https://github.com/radarsh/gradle-test-logger-plugin

[]: https://github.com/jlleitschuh/ktlint-gradle