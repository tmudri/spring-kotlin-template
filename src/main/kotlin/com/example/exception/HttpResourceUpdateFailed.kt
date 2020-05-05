package com.example.exception

import java.lang.RuntimeException

class HttpResourceUpdateFailed(message: String) : RuntimeException(message)
