package com.example.customer.integration

import com.example.customer.domain.Customer
import com.example.customer.entity.CustomerEntity
import com.example.customer.repository.CustomerRepository
import com.example.customer.service.CustomerService
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerServiceIntegrationTest {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var customerService: CustomerService

    var id: Long? = null

    @Before
    fun init() {
        val customerEntity = CustomerEntity(
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )

        id = customerRepository.saveAndFlush(customerEntity).id
    }

    @After
    fun cleanUp() {
        customerRepository.deleteAll()
    }

    @Test
    fun shouldFindByExistingId() {
        val expectedCustomer = Customer(
                id = id,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )

        val returnedCustomer = id?.let {
            customerService.findById(it)
        }

        assertEquals(expectedCustomer, returnedCustomer)
    }

    @Test
    fun shouldNotFindAndReturnNullWithNonExistingId() {
        val returnedCustomer = id?.let {
            customerService.findById(it + 1)
        }

        assertNull(returnedCustomer)
    }

    @Test
    fun shouldSaveNewCustomer() {
        val customer = Customer(
                firstName = "Jane",
                lastName = "Doe",
                gender = "female",
                address = "Magic Street 2",
                phone = "+49 1111 22 33"
        )

        val returnedCustomerId = customerService.save(customer)

        assertNotNull(returnedCustomerId)
    }

    @Test
    fun shouldUpdateNewCustomer() {
        val customerEntity = CustomerEntity(
                firstName = "Marc",
                lastName = "Man",
                gender = "male",
                address = "Super Street 1",
                phone = "+49 1111 222 33 44"
        )

        val savedCustomer = customerRepository.saveAndFlush(customerEntity)

        val customerToUpdate = Customer(
                id = savedCustomer.id,
                firstName = "Marco",
                lastName = "Pollo",
                gender = "male",
                address = "Regular Street 1",
                phone = "+49 1111 222 33 45"
        )

        val savedUpdatedCustomer = customerService.save(customerToUpdate)
        val foundUpdatedCustomer = savedCustomer.id?.let {
            customerService.findById(it)
        }

        assertNotNull(savedUpdatedCustomer.id)
        assertNotNull(foundUpdatedCustomer?.id)

        assertEquals(customerToUpdate, savedUpdatedCustomer)
        assertEquals(customerToUpdate, foundUpdatedCustomer)
    }

    @Test
    fun shouldDeleteNewCustomer() {
        val customerEntity = CustomerEntity(
                firstName = "Ann",
                lastName = "Lady",
                gender = "female",
                address = "Awesome Street 100",
                phone = "+1 222 333 44 55"
        )

        val savedCustomer = customerRepository.saveAndFlush(customerEntity)

        val deletedCustomer = savedCustomer.id?.let {
            customerRepository.deleteById(it)
            customerService.findById(it)
        }

        assertNotNull(savedCustomer.id)
        assertNull(deletedCustomer)
    }

    private fun addToList(id: Long?, list: ArrayList<Long>) {
        id?.let {
            list.add(it)
        }
    }
}
