package com.example.repository

import com.example.entity.CustomerEntity
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CustomerRepositoryTest {

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

        val savedCustomer = customerRepository.saveAndFlush(customer)
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

        val savedCustomer = customerRepository.saveAndFlush(customer)
        customerRepository.delete(savedCustomer)
        val deletedCustomer = savedCustomer.id?.let {
            customerRepository.findById(it)
        }

        assertNotNull(savedCustomer.id)
        assertNotNull(deletedCustomer)
        assertFalse(deletedCustomer!!.isPresent)
    }
}
