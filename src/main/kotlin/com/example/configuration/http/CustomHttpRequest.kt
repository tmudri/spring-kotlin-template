package com.example.configuration.http

import com.example.extensions.toEnumeration
import com.example.extensions.whenNull
import java.util.Enumeration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class CustomHttpRequest(request: HttpServletRequest?) : HttpServletRequestWrapper(request) {

    private val headersMap = HashMap<String, List<String>>()

    init {
        request
                ?.headerNames
                ?.iterator()
                ?.forEach {
                    headersMap[it] = request.getHeaders(it).toList()
                }
    }

    fun putHeader(key: String, value: String) {
        headersMap[key] = arrayListOf(value)
    }

    override fun getHeader(name: String?): String? =
            headersMap[name]
                    ?.get(0)
                    .whenNull(super.getHeader(name))

    override fun getHeaders(name: String?): Enumeration<String> =
            headersMap[name]
                    .whenNull(ArrayList())
                    .toEnumeration()

    override fun getHeaderNames(): Enumeration<String> =
            headersMap
                    .keys
                    .toEnumeration()
}
