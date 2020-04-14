package com.example.configuration.http.filter

import com.example.configuration.http.CustomHttpRequest
import com.example.extensions.whenNull
import java.util.UUID
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val CORRELATION_ID = "X-Correlation-ID"

class CorrelationIdFilter : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        checkNotNull(request) { "Servlet request cannot be null" }
        checkNotNull(response) { "Servlet response cannot be null" }

        val cid = getCorrelationId(request as HttpServletRequest)

        val httpRequest = addCorrelationIdToRequestHeader(request, cid)
        val httpResponse = addCorrelationIdToResponseHeader(response as HttpServletResponse, cid)

        chain?.doFilter(httpRequest, httpResponse)
    }

    private fun getCorrelationId(request: HttpServletRequest?) =
            request
                ?.getHeader(CORRELATION_ID)
                .whenNull(UUID.randomUUID().toString())

    private fun addCorrelationIdToRequestHeader(request: HttpServletRequest?, correlationId: String): HttpServletRequest {
        val customHttpRequest = CustomHttpRequest(request)
        customHttpRequest.putHeader(CORRELATION_ID, correlationId)

        return customHttpRequest
    }

    private fun addCorrelationIdToResponseHeader(response: HttpServletResponse?, correlationId: String): HttpServletResponse? {
        response?.setHeader(CORRELATION_ID, correlationId)

        return response
    }
}
