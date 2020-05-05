package com.example.customer.repository

import com.example.customer.entity.CustomerEntity
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryIntegrationTest {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Test
    fun shouldSaveUser() {
        val customer = CustomerEntity(
                firstName = "Mark",
                lastName = "Summer",
                gender = "Male",
                address = "First street 1",
                phone = "+49 1600 5546 200"
        )

        val savedCustomer = customerRepository.save(customer)
        val loadedCustomer = savedCustomer.id?.let {
            customerRepository.findById(it)
        }

        assertNotNull(savedCustomer.id)
        assertNotNull(loadedCustomer)
        assertTrue(loadedCustomer!!.isPresent)
        assertEquals(savedCustomer, loadedCustomer.get())
    }

    @Test
    fun shouldDeleteUser() {
        val customer = CustomerEntity(
                firstName = "Ann",
                lastName = "Spring",
                gender = "Female",
                address = "First street 1",
                phone = "+49 1501 1234 567"
        )

        val savedCustomer = customerRepository.save(customer)

        val deletedCustomer = savedCustomer.id?.let {
            customerRepository.deleteById(it)
            customerRepository.findById(it)
        }

        assertNotNull(savedCustomer.id)
        assertNotNull(deletedCustomer)
        assertFalse(deletedCustomer!!.isPresent)
    }

    @Test
    fun shouldThrowExceptionOnDeleteWhenCustomerNotExists() {
        Assertions.assertThrows(EmptyResultDataAccessException::class.java) {
            customerRepository.deleteById(10001)
        }
    }
}
