package com.example.customer.controller

import com.example.customer.domain.Customer
import com.example.customer.service.CustomerService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus

class CustomerControllerTest {

    companion object Headers {
        const val HEADER_LOCATION = "Location"
    }

    @Test
    fun shouldReturnCustomerWhenFindByExistingIdSucceedsHttpStatusCode200() {
        val id = 1L
        val customerService = mock(CustomerService::class.java)
        val customerController = CustomerController(customerService)
        val customer = Customer(
                id = id,
                firstName = "Jane",
                lastName = "Doe",
                gender = "female",
                address = "Magic Street 112",
                phone = "+1 1234 555 66 77"
        )

        `when`(customerService.findById(id)).thenReturn(customer)

        val response = customerController.findById(id)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customer, response.body)
    }

    @Test
    fun shouldThrowExceptionWhenFindingCustomerByIdFails() {
        val id = 2L
        val customerService = mock(CustomerService::class.java)
        val customerController = CustomerController(customerService)

        `when`(customerService.findById(id)).thenReturn(null)

        assertThrows(IllegalArgumentException::class.java) {
            customerController.findById(id)
        }
    }

    @Test
    fun shouldSaveNonExistingCustomerAndReturnIdAndHttpStatusCode201() {
        val id = 3L
        val customerService = mock(CustomerService::class.java)
        val customerController = CustomerController(customerService)
        val newCustomer = Customer(
                firstName = "Jane",
                lastName = "Doe",
                gender = "female",
                address = "Magic Street 112",
                phone = "+1 1234 555 66 77"
        )
        val savedCustomer = Customer(
                id = id,
                firstName = "Jane",
                lastName = "Doe",
                gender = "female",
                address = "Magic Street 112",
                phone = "+1 1234 555 66 77"
        )

        `when`(customerService.save(newCustomer)).thenReturn(savedCustomer)

        val response = customerController.save(newCustomer)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(1, response.headers[HEADER_LOCATION]?.size)
        assertEquals("/api/v1/customer/$id", response.headers[HEADER_LOCATION]?.get(0))
        assertEquals(id, response.body)
    }

    @Test
    fun shouldUpdateNonExistingCustomerAndReturnHttpStatusCode200() {
        val id = 4L
        val customerService = mock(CustomerService::class.java)
        val customerController = CustomerController(customerService)
        val customer = Customer(
                id = id,
                firstName = "Jane",
                lastName = "Doe",
                gender = "female",
                address = "Magic Street 112",
                phone = "+1 1234 555 66 77"
        )

        `when`(customerService.save(customer)).thenReturn(customer)

        val response = customerController.save(customer)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun shouldDeleteExistingCustomerAndReturnHttpStatusCode204() {
        val id = 5L
        val customerService = mock(CustomerService::class.java)
        val customerController = CustomerController(customerService)

        val response = customerController.delete(id)

        verify(customerService).delete(id)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun shouldWhenDeletingNonExistingCustomerReturnHttpStatusCode400() {
        val id = 6L
        val customerService = mock(CustomerService::class.java)
        val customerController = CustomerController(customerService)

        `when`(customerService.delete(id)).thenThrow(EmptyResultDataAccessException::class.java)

        assertThrows(EmptyResultDataAccessException::class.java) {
            customerController.delete(id)
        }
    }
}
