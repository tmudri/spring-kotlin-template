package com.example.customer.service

import com.example.customer.domain.Customer
import com.example.customer.entity.CustomerEntity
import com.example.customer.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerService(@Autowired val customerRepository: CustomerRepository) {

    fun findById(id: Long): Customer? =
        customerRepository
                .findById(id)
                .map { Customer(it) }
                .orElseGet { null }

    fun save(customer: Customer): Customer =
            customerRepository
                    .save(CustomerEntity(customer))
                    .let { Customer(it) }

    fun delete(id: Long) = customerRepository.deleteById(id)
}
