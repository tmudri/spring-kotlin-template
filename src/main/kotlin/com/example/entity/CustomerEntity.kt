package com.example.entity

import javax.persistence.Column
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
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ_GEN") @Column(name = "ID")val id: Long? = null,
    @Column(name = "FIRST_NAME") val firstName: String? = null,
    @Column(name = "LAST_NAME") val lastName: String? = null,
    @Column(name = "GENDER") val gender: String? = null,
    @Column(name = "ADDRESS") val address: String? = null,
    @Column(name = "PHONE") val phone: String? = null
)
