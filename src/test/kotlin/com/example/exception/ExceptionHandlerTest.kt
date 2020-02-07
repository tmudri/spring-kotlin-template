package com.example.exception

import com.example.exception.ErrorCodes.GENERAL_EXCEPTION
import com.example.exception.ErrorCodes.ILLEGAL_ARGUMENT
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ExceptionHandlerTest {

    @Test
    fun shouldReturnBadRequestForAllIllegalArgumentException() {
        val handler = ExceptionHandler()

        val response = handler.handleIllegalArgumentException(IllegalArgumentException("Message!"))

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorResponse(ILLEGAL_ARGUMENT.toString(), listOf("Message!")), response.body)
    }

    @Test
    fun shouldReturnGeneralExceptionForAllNonSpecificMessages() {
        val handler = ExceptionHandler()

        val response = handler.handleGeneralException(RuntimeException("General message!"))

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(ErrorResponse(GENERAL_EXCEPTION.toString(), listOf("General message!")), response.body)
    }

    @Test
    fun shouldReturnGeneralRuntimeExceptionForAllNonSpecificMessages() {
        val handler = ExceptionHandler()

        val response = handler.handleGeneralException(RuntimeException("General runtime message!"))

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(ErrorResponse(GENERAL_EXCEPTION.toString(), listOf("General runtime message!")), response.body)
    }

    @Test
    fun shouldReturnNumberFormatExceptionAsGeneralException() {
        val handler = ExceptionHandler()

        val response = handler.handleGeneralException(NumberFormatException("Number format exception message!"))

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(ErrorResponse(GENERAL_EXCEPTION.toString(), listOf("Number format exception message!")), response.body)
    }
}
