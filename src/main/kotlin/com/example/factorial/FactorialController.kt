package com.example.factorial

import com.example.factorial.domain.Factorial
import com.example.factorial.service.FactorialService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController(value = "/api/v1.0/factorial")
class FactorialController(
        @Autowired val factorialService: FactorialService
) {

    @GetMapping("/{number}")
    fun factorial(@PathVariable number: Int): ResponseEntity<Factorial> =
        ResponseEntity
                .ok()
                .body(factorialService.calculateFactorialNumberOf(number))

}