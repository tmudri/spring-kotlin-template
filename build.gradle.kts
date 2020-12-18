import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
    id("com.adarshr.test-logger") version "2.0.0"
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
        exclude(group = "junit", module = "junit")
    }
    testImplementation("org.junit.platform:junit-platform-runner:1.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")

    implementation("io.cucumber:cucumber-spring:5.1.0")
    implementation("io.cucumber:cucumber-jvm:5.1.0")
    implementation("io.cucumber:cucumber-java8:5.1.0")
    implementation("io.cucumber:cucumber-junit:5.1.0")
    testImplementation("de.monochromata.cucumber:reporting-plugin:4.0.40")
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
    systemProperty("cucumber.reporting.config.file", "./src/behavior-test/resources/cucumber-reporting.properties")
    description = "Runs behavior tests."
    group = "verification"

    testClassesDirs = sourceSets["behavior-test"].output.classesDirs
    classpath = sourceSets["behavior-test"].runtimeClasspath
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
