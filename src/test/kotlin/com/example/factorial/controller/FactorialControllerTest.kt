package com.example.factorial.controller

import com.example.factorial.domain.Factorial
import com.example.factorial.service.FactorialService
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus

class FactorialControllerTest() {

    @Test
    fun shouldReturnCorrectFactorialOnPositiveFactorialRequest() {
        val factorialService = mock(FactorialService::class.java)
        val factorialController = FactorialController(factorialService)
        val factorialOfEight = Factorial(8, 40320)

        `when`(factorialService.calculateFactorialNumberOf(8)).thenReturn(factorialOfEight)

        val response = factorialController.factorial(8)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(factorialOfEight, response.body)
    }

    @Test
    fun shouldThrowIllegalArgumentExceptionOnNegativeFactorialRequest() {
        val factorialService = mock(FactorialService::class.java)
        val factorialController = FactorialController(factorialService)

        `when`(factorialService.calculateFactorialNumberOf(-2)).thenThrow(IllegalArgumentException::class.java)

        assertThrows<IllegalArgumentException> {
            factorialController.factorial(-2)
        }
    }

    @Test
    fun shouldThrowRuntimeExceptionThrownByGeneralExceptionMapping() {
        val factorialService = mock(FactorialService::class.java)
        val factorialController = FactorialController(factorialService)

        assertThrows<RuntimeException> {
            factorialController.generalExceptionMapping()
        }
    }
}
