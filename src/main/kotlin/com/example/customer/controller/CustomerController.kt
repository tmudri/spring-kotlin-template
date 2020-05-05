package com.example.customer.controller

import com.example.customer.domain.Customer
import com.example.customer.service.CustomerService
import com.example.exception.HttpResourceUpdateFailed
import com.example.extensions.throwIfNull
import java.net.URI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customer")
class CustomerController(
    @Autowired val customerService: CustomerService
) {

    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable id: Long) =
        customerService
                .findById(id)
                .throwIfNull(IllegalArgumentException("Customer with id $id does not exist"))
                .let {
                    ResponseEntity
                            .ok()
                            .body(it)
                }

    @PutMapping
    fun save(@RequestBody customer: Customer) =
        when (val returnedCustomerId = customerService.save(customer).id) {
            null -> throw HttpResourceUpdateFailed("Failed to save customer with id ${customer.id}")
            customer.id ->
                ResponseEntity
                        .ok()
                        .build()
            else ->
                ResponseEntity
                    .created(URI.create("/api/v1/customer/$returnedCustomerId"))
                    .body(returnedCustomerId)
        }

    @PostMapping(path = ["/{id}"])
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        customerService.delete(id)
        return ResponseEntity
                .noContent()
                .build()
    }
}
