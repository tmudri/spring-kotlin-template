package com.example.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice(basePackages = ["com.example"])
class ExceptionHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<ErrorResponse> =
            ResponseEntity
                    .badRequest()
                    .body(ErrorResponse(ErrorCodes.ILLEGAL_ARGUMENT.name, listOf(exception.message ?: "")))

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGeneralException(exception: Exception) =
            ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse(ErrorCodes.GENERAL_EXCEPTION.name, listOf(exception.message ?: "")))
}
