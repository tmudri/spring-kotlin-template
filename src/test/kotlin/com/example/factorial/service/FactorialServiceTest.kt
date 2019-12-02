package com.example.factorial.service

import com.example.factorial.domain.Factorial
import java.lang.IllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FactorialServiceTest {

    @Test
    fun shouldCalculatePositiveValue() {
        val factorialService = FactorialService()

        assertEquals(
                Factorial(5, 120),
                factorialService.calculateFactorialNumberOf(5)
        )
    }

    @Test
    fun shouldCalculateValueForNumberOne() {
        val factorialService = FactorialService()

        assertEquals(
                Factorial(1, 1),
                factorialService.calculateFactorialNumberOf(1)
        )
    }

    @Test
    fun shouldThrowExceptionForNegativeInput() {
        val factorialService = FactorialService()

        assertThrows<IllegalArgumentException>("Unsupported value: -2. Cannot calculate factorial value for negative numbers or zero.") {
            factorialService.calculateFactorialNumberOf(-2)
        }
    }
}
