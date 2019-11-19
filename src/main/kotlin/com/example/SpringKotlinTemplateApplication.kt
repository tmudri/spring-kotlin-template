package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinTemplateApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinTemplateApplication>(*args)
}
