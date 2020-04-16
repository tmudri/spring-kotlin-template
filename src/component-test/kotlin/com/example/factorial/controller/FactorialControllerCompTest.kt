package com.example.factorial.controller

import com.example.factorial.domain.Factorial
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class FactorialControllerCompTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun shouldCalculatePositiveValue() {
        val url = "/api/v1/factorial/8"

        val response = restTemplate.getForEntity<Factorial>(url)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Factorial(8, 40320), response.body)
    }

    @Test
    fun shouldCalculateValueForNumberOne() {
        val url = "/api/v1/factorial/1"

        val response = restTemplate.getForEntity<Factorial>(url)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Factorial(1, 1), response.body)
    }

    @Test
    fun shouldReturnBadRequestForIllegalFactorialNumbers() {
        val url = "/api/v1/factorial/-2"

        val response = restTemplate.getForEntity<String>(url)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun shouldReturnIllegalServerErrorForGeneralExceptions() {
        val url = "/api/v1/factorial/general_exception"

        val response = restTemplate.getForEntity(url, Factorial::class.java)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }
}
