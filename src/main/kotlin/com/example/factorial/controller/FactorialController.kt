package com.example.factorial.controller

import com.example.factorial.domain.Factorial
import com.example.factorial.service.FactorialService
import java.lang.RuntimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/factorial")
class FactorialController(
    @Autowired val factorialService: FactorialService
) {

    @GetMapping(path = [ "/{number}" ])
    fun factorial(@PathVariable number: Int): ResponseEntity<Factorial> =
        ResponseEntity
                .ok()
                .body(factorialService.calculateFactorialNumberOf(number))

    @GetMapping(path = [ "/general_exception" ])
    fun generalExceptionMapping(): ResponseEntity<Factorial> =
            throw RuntimeException("Generated message.")
}
