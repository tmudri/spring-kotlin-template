package com.example

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration
class SpringKotlinTemplateApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinTemplateApplication>(*args)
}
