package com.example.configuration.web

import com.example.configuration.http.filter.CorrelationIdFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebMvcConfig {

    @Bean
    fun loggingFilter(): FilterRegistrationBean<CorrelationIdFilter>? {
        val registrationBean = FilterRegistrationBean<CorrelationIdFilter>()
        registrationBean.filter = CorrelationIdFilter()
        return registrationBean
    }
}
