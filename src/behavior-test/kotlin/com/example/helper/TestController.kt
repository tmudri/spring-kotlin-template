package com.example.helper

import com.example.configuration.http.filter.CORRELATION_ID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController {

    @GetMapping(path = [ "/correlation_id" ])
    fun factorial(@RequestHeader(name = CORRELATION_ID) correlationId: String?): ResponseEntity<String> =
            ResponseEntity
                .ok()
                .body(correlationId)
}
