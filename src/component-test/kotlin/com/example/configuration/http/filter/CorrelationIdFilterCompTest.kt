package com.example.configuration.http.filter

import java.net.URI
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.test.context.junit.jupiter.SpringExtension

// TODO: Use in-memory DB
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CorrelationIdFilterCompTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    private val url = "/api/v1/test/correlation_id"

    @Test
    fun shouldAddCorrelationIdToRequestAndResponseWhenNotExists() {
        val request = RequestEntity<String>(HttpHeaders(), HttpMethod.GET, URI.create(url))

        val response = restTemplate.exchange(request, String::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(response.body.toString().isNotEmpty())
        assertEquals(response.body.toString(), response.headers[CORRELATION_ID]?.get(0))
    }

    @Test
    fun shouldUseExistingCorrelationIdInRequestAndResponseWhenExists() {
        val cid = UUID.randomUUID().toString()

        val headers = HttpHeaders()
        headers.set(CORRELATION_ID, cid)
        val request = RequestEntity<String>(headers, HttpMethod.GET, URI.create(url))

        val response = restTemplate.exchange(request, String::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(cid, response.body.toString())
        assertEquals(cid, response.headers[CORRELATION_ID]?.get(0))
    }
}
