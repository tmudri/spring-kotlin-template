package com.example.factorial.controller

import com.example.exception.ErrorCodes
import com.example.factorial.domain.Factorial
import com.example.factorial.service.UNSUPPORTED_FACTORIAL_VALUE
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FactorialControllerTest(
    @Autowired var mockMvc: MockMvc,
    @Autowired val mapper: ObjectMapper
) {

    @Test
    fun shouldReturnFactorialOfSeven() {
        val result = mockMvc
                .get("/api/v1/factorial/7")
                .andExpect {
                    status { isOk }
                }
                .andReturn()

        val stringValue = result.response.contentAsString
        val value = mapper.readValue(stringValue, Factorial::class.java)

        assertEquals(Factorial(7, 5040), value)
    }

    @Test
    fun shouldReturnErrorResponseOnNegativeFactorialRequest() {
        mockMvc
                .get("/api/v1/factorial/-2")
                .andExpect {
                    status { isBadRequest }
                    content {
                        json("{\"errorCode\":\"${ErrorCodes.ILLEGAL_ARGUMENT.name}\",\"errorMessages\":[\"${String.format(UNSUPPORTED_FACTORIAL_VALUE, -2)}\"]}", true)
                    }
                }
                .andReturn()
    }

    @Test
    fun shouldReturnInternalServerErrorWhenExceptionThrownByControllerMethod() {
        mockMvc
                .get("/api/v1/factorial/general_exception")
                .andExpect {
                    status { isInternalServerError }
                    content {
                        json("{\"errorCode\":\"${ErrorCodes.GENERAL_EXCEPTION.name}\",\"errorMessages\":[\"Generated message.\"]}", true)
                    }
                }
                .andReturn()
    }
}
