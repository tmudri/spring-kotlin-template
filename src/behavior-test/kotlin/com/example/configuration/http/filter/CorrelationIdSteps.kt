package com.example.configuration.http.filter

import io.cucumber.java8.En
import java.net.URI
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class CorrelationIdSteps : En {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    var url = "/api/v1/test/correlation_id"
    var request: RequestEntity<String>? = null
    var response: ResponseEntity<String>? = null

    init {
        Given("correlation ID does not exists in http request header") {
            request = RequestEntity(HttpHeaders(), HttpMethod.GET, URI.create(url))
        }

        When("request is processed") {
            request?.apply {
                response = restTemplate.exchange(this, String::class.java)
            }
        }

        Then("new correlation ID is created and set to request and response X-Correlation-ID headers") {
            assertEquals(HttpStatus.OK, response?.statusCode)
            assertTrue(response?.body.toString().isNotEmpty())
            assertEquals(response?.body?.toString(), response?.headers?.get("X-Correlation-ID")?.get(0))
        }

        Given<String, String>("correlation ID {string} exists in http request header {string}") { cid, headerKey ->
            val headers = HttpHeaders()
            headers.set(headerKey, cid)
            request = RequestEntity(headers, HttpMethod.GET, URI.create(url))
        }

        Then<String, String>("correlation ID {string} is set to request and response headers header {string}") { cid, headerKey ->
            assertEquals(HttpStatus.OK, response?.statusCode)
            assertEquals(cid, response?.body?.toString())
            assertEquals(cid, response?.headers?.get(headerKey)?.get(0))
        }
    }
}
