package com.example.configuration.http

import com.example.configuration.http.filter.CORRELATION_ID
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest

class CustomHttpRequestTest {

    private val randomHeaderKey = "RANDOM_HEADER_KEY"
    private val randomHeaderValue1 = "RANDOM_HEADER_VALUE1"
    private val randomHeaderValue2 = "RANDOM_HEADER_VALUE2"

    @Test
    fun shouldReturnNewlyAddedHeader() {
        val cid = UUID.randomUUID().toString()

        val request = CustomHttpRequest(MockHttpServletRequest())
        request.putHeader(CORRELATION_ID, cid)

        assertNotNull(request.getHeader(CORRELATION_ID))
        assertEquals(cid, request.getHeader(CORRELATION_ID))
    }

    @Test
    fun shouldReturnExistingHeaderWhenNewNotAdded() {
        val mockRequest = MockHttpServletRequest()
        mockRequest.addHeader(randomHeaderKey, randomHeaderValue1)

        val request = CustomHttpRequest(mockRequest)

        assertNotNull(request.getHeader(randomHeaderKey))
        assertEquals(randomHeaderValue1, request.getHeader(randomHeaderKey))
    }

    @Test
    fun shouldReturnNewlyAddedHeaders() {
        val cid = UUID.randomUUID().toString()

        val request = CustomHttpRequest(MockHttpServletRequest())
        request.putHeader(CORRELATION_ID, cid)

        val headers =
                request
                        .getHeaders(CORRELATION_ID)
                        .toList()

        assertEquals(1, headers.size)
        assertEquals(cid, headers.get(0))
    }

    @Test
    fun shouldReturnExistingHeadersWhenNewNotAdded() {
        val mockRequest = MockHttpServletRequest()
        mockRequest.addHeader(randomHeaderKey, randomHeaderValue1)
        mockRequest.addHeader(randomHeaderKey, randomHeaderValue2)

        val headers =
                CustomHttpRequest(mockRequest)
                    .getHeaders(randomHeaderKey)
                    .toList()

        assertEquals(2, headers.size)
        assertTrue(headers.contains(randomHeaderValue1))
        assertTrue(headers.contains(randomHeaderValue2))
    }

    @Test
    fun shouldReturnAllHeaderNames() {
        val cid = UUID.randomUUID().toString()
        val mockRequest = MockHttpServletRequest()
        mockRequest.addHeader(randomHeaderKey, randomHeaderValue1)

        val request = CustomHttpRequest(mockRequest)
        request.putHeader(CORRELATION_ID, cid)

        val headerNames =
                request
                        .headerNames
                        .toList()

        assertEquals(2, headerNames.size)
        assertTrue(headerNames.contains(CORRELATION_ID))
        assertTrue(headerNames.contains(randomHeaderKey))
    }
}
