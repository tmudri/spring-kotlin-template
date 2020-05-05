package com.example.customer.entity

import com.example.customer.domain.Customer
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "CUSTOMER")
@SequenceGenerator(name = "CUSTOMER_SEQ_GEN", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
data class CustomerEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ_GEN") val id: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val address: String? = null,
    val phone: String? = null
) {
    constructor(customer: Customer) : this(
            id = customer.id,
            firstName = customer.firstName,
            lastName = customer.lastName,
            gender = customer.gender,
            address = customer.address,
            phone = customer.phone
    )
}
