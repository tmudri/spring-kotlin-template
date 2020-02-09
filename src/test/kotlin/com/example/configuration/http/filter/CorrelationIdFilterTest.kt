package com.example.configuration.http.filter

import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class CorrelationIdFilterTest {

    @Test
    fun shouldCreateMissingCorrelationIdAndAddItToRequestAndResponse() {
        val filter = CorrelationIdFilter()
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val chain = MockFilterChain()

        filter.doFilter(request, response, chain)

        val requestHeaderCid = (chain.request as HttpServletRequest).getHeader(CORRELATION_ID)
        val responseHeaderCid = (chain.response as HttpServletResponse).getHeader(CORRELATION_ID)

        assertNotNull(requestHeaderCid)
        assertNotNull(responseHeaderCid)
        assertTrue(requestHeaderCid.isNotEmpty())
        assertTrue(responseHeaderCid.isNotEmpty())
        assertEquals(requestHeaderCid, responseHeaderCid)
    }

    @Test
    fun shouldAddExistingCorrelationIdFromRequestToResponse() {
        val cid = UUID.randomUUID().toString()
        val filter = CorrelationIdFilter()
        val request = MockHttpServletRequest()
        request.addHeader(CORRELATION_ID, cid)
        val response = MockHttpServletResponse()
        val chain = MockFilterChain()

        filter.doFilter(request, response, chain)

        val requestHeaderCid = (chain.request as HttpServletRequest).getHeader(CORRELATION_ID)
        val responseHeaderCid = (chain.response as HttpServletResponse).getHeader(CORRELATION_ID)

        assertEquals(cid, requestHeaderCid)
        assertEquals(cid, responseHeaderCid)
    }
}
