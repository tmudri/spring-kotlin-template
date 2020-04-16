package com.example.configuration

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.example.repository"])
class Configuration {

    @Bean
    fun javaTimeModule(): Module {
        return JavaTimeModule()
    }

    @Bean
    fun jdk8Module(): Module {
        return Jdk8Module()
    }
}
