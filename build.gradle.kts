import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.util.Date

plugins {
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.60"
    kotlin("plugin.spring") version "1.3.60"
}


group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_12

repositories {
    mavenCentral()
}

sourceSets {
    create("component-test") {
        java.srcDir("src/component-test/kotlin")
        resources.srcDir("src/component-test/resources")
        compileClasspath += sourceSets.main.get().output + configurations.testRuntimeClasspath
        runtimeClasspath += output + compileClasspath
    }
}

sourceSets {
    create("integration-test") {
        java.srcDir("src/integration-test/kotlin")
        resources.srcDir("src/integration-test/resources")
        compileClasspath += sourceSets.main.get().output + configurations.testRuntimeClasspath
        runtimeClasspath += output + compileClasspath
    }
}


sourceSets {
    create("behavior-test") {
        java.srcDir("src/behavior-test/kotlin")
        resources.srcDir("src/behavior-test/resources")
        compileClasspath += sourceSets.main.get().output + configurations.testRuntimeClasspath
        runtimeClasspath += output + compileClasspath
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}


tasks.register<Test>("comp-test") {
    useJUnitPlatform()
    description = "Runs component tests."
    group = "verification"

    testClassesDirs = sourceSets["component-test"].output.classesDirs
    classpath = sourceSets["component-test"].runtimeClasspath
}

tasks.register<Test>("int-test") {
    useJUnitPlatform()
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integration-test"].output.classesDirs
    classpath = sourceSets["integration-test"].runtimeClasspath
}

tasks.register<Test>("behavior-test") {
    useJUnitPlatform()
    description = "Runs behavior tests."
    group = "verification"

    testClassesDirs = sourceSets["behavior-test"].output.classesDirs
    classpath = sourceSets["behavior-test"].runtimeClasspath
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "12"
    }
}

rootProject.apply {

    ext.set("testResults", arrayListOf<String>())

    tasks.withType(Test::class.java) {
        this.testLogging {
            events(
                    TestLogEvent.FAILED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_OUT,
                    TestLogEvent.STANDARD_ERROR
            )

            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = false
        }

        this.ignoreFailures = false

        val testTask = this
        val testResults = arrayListOf<String>()

        this.afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->

            if (desc.parent != null) return@KotlinClosure2 // Only summarize results for whole modules

            val summary: String = "${testTask.project.name}:${testTask.name} results: ${result.resultType} " +
                    "(" +
                    "${result.testCount} tests, " +
                    "${result.successfulTestCount} successes, " +
                    "${result.failedTestCount} failures, " +
                    "${result.skippedTestCount} skipped" +
                    ") " +
                    "in ${groovy.time.TimeCategory.minus(Date(result.endTime), Date(result.startTime))}" +
                    "\n" +
                    "Report file: ${testTask.reports.html.entryPoint}"

            // Add reports in `testsResults`, keep failed suites at the end
            if (result.resultType == TestResult.ResultType.SUCCESS) {
                testResults.add(summary)
            } else {
                testResults.add(0, summary)
            }

            rootProject.ext.set("testResults", testResults)
        })
        )
    }
}


gradle.buildFinished {
    val allResults = rootProject.ext.get("testResults")

    allResults.apply {
        if (allResults is ArrayList<*>) {
            printResults(allResults)
        }
    }
}

fun printResults(allResults: ArrayList<*>) {
    val maxLength = allResults
            .map {
                println(it)
                it
            }
            .map { it.toString().length }
            .max()

    maxLength?.apply {
        print("┌")
        for (i in 1..maxLength) print("-")
        println("┐")


        allResults.forEach {
            it.toString().lines().forEach { line ->
                print("│")
                print(line)
                print(" ".repeat(maxLength - line.length))
                println("│")
            }
        }

        print("┌")
        for (i in 1..maxLength) print("-")
        println("┘")
    }

}