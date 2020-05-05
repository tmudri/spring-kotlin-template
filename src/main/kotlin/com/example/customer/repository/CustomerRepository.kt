package com.example.customer.repository

import com.example.customer.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<CustomerEntity, Long>
