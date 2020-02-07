package com.example.exception

enum class ErrorCodes {
    ILLEGAL_ARGUMENT,
    GENERAL_EXCEPTION
}

data class ErrorResponse(val errorCode: String, val errorMessages: List<String>)
