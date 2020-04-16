package com.example.customer.repository

import com.example.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<CustomerEntity, Long>
