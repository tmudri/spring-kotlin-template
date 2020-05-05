package com.example.customer.service

import com.example.customer.domain.Customer
import com.example.customer.entity.CustomerEntity
import com.example.customer.repository.CustomerRepository
import java.util.Optional
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CustomerServiceTest {

    @Test
    fun shouldFindByExistingId() {
        val customerRepository = mock(CustomerRepository::class.java)
        val customerService = CustomerService(customerRepository)
        val id = 1L
        val customerEntity = CustomerEntity(
                id = id,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )
        val expectedCustomer = Customer(
                id = id,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )

        `when`(customerRepository.findById(id)).thenReturn(Optional.of(customerEntity))

        val returnedCustomer = customerService.findById(id)

        assertEquals(expectedCustomer, returnedCustomer)
    }

    @Test
    fun shouldNotFindAndReturnNullWithNonExistingId() {
        val customerRepository = mock(CustomerRepository::class.java)
        val customerService = CustomerService(customerRepository)
        val id = 1L

        `when`(customerRepository.findById(id)).thenReturn(Optional.empty())

        val returnedCustomer = customerService.findById(id)

        assertNull(returnedCustomer)
    }

    @Test
    fun shouldSaveNewCustomer() {
        val customerRepository = mock(CustomerRepository::class.java)
        val customerService = CustomerService(customerRepository)
        val id = 1L
        val customerEntity = CustomerEntity(
                id = null,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )
        val customerEntitySaved = CustomerEntity(
                id = id,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )
        val customer = Customer(
                id = null,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )
        val expectedCustomer = Customer(
                id = id,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )

        `when`(customerRepository.save(customerEntity)).thenReturn(customerEntitySaved)

        val returnedCustomer = customerService.save(customer)

        assertEquals(expectedCustomer, returnedCustomer)
    }

    @Test
    fun shouldUpdateNewCustomer() {
        val customerRepository = mock(CustomerRepository::class.java)
        val customerService = CustomerService(customerRepository)
        val id = 1L
        val customerEntity = CustomerEntity(
                id = id,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )

        val customer = Customer(
                id = id,
                firstName = "John",
                lastName = "Doe",
                gender = "male",
                address = "Magic Street 1",
                phone = "+49 1234 555 66 77"
        )

        `when`(customerRepository.save(customerEntity)).thenReturn(customerEntity)

        val returnedCustomer = customerService.save(customer)

        assertNotNull(returnedCustomer)
        assertEquals(customer, returnedCustomer)
    }

    @Test
    fun shouldDeleteNewCustomer() {
        val customerRepository = mock(CustomerRepository::class.java)
        val customerService = CustomerService(customerRepository)
        val id = 1L

        customerService.delete(id)

        verify(customerRepository).deleteById(id)
    }
}
