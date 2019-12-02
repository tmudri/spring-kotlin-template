package com.example.factorial.service

import com.example.factorial.domain.Factorial
import java.lang.IllegalArgumentException
import java.util.stream.IntStream
import org.springframework.stereotype.Service

val UNSUPPORTED_FACTORIAL_VALUE = "Unsupported value: %d. Cannot calculate factorial value for negative numbers or zero."

@Service
class FactorialService {

    fun calculateFactorialNumberOf(value: Int): Factorial {
        if (value < 1) {
            throw IllegalArgumentException(UNSUPPORTED_FACTORIAL_VALUE.format(value))
        }
        return Factorial(
                factorial = value,
                value = IntStream
                        .rangeClosed(1, value)
                        .reduce { a, b ->
                            a * b
                        }.asInt
        )
    }
}
