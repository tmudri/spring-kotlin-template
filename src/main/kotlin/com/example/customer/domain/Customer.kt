package com.example.customer.domain

import com.example.customer.entity.CustomerEntity

data class Customer(
    val id: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val address: String? = null,
    val phone: String? = null
) {
    constructor(entity: CustomerEntity) : this(
            id = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            gender = entity.gender,
            address = entity.address,
            phone = entity.phone
    )
}
