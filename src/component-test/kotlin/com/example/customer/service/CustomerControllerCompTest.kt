package com.example.customer.service

import com.example.customer.domain.Customer
import com.example.customer.entity.CustomerEntity
import com.example.customer.repository.CustomerRepository
import com.example.exception.ErrorCodes
import com.example.exception.ErrorResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner

// TODO: Write it to not use real DB, but in memory one
@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerCompTest {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    var id: Long? = null

    @Before
    fun init() {
        val customerEntity = CustomerEntity(
                firstName = "Marie",
                lastName = "Curie",
                gender = "female",
                address = "Scientist Street 1",
                phone = "+1 1234 555 66 77"
        )

        id = customerRepository.saveAndFlush(customerEntity).id
    }

    @After
    fun cleanUp() {
        id?.let {
            customerRepository.deleteById(it)
        }
    }

    @Test
    fun shouldFindByExistingId() {
        val url = "/api/v1/customer/$id"

        val expectedCustomer = Customer(
                id = id,
                firstName = "Marie",
                lastName = "Curie",
                gender = "female",
                address = "Scientist Street 1",
                phone = "+1 1234 555 66 77"
        )

        val response = restTemplate.getForEntity<Customer>(url)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedCustomer, response.body)
    }

    @Test
    fun shouldNotFindNonExistingCustomerAndReturnBadRequest() {
        val nonExistingId = id?.let { it + 999 }
        val url = "/api/v1/customer/$nonExistingId"

        val response = restTemplate.getForEntity<ErrorResponse>(url)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(ErrorCodes.ILLEGAL_ARGUMENT.name, response.body?.errorCode)
        assertEquals(1, response.body?.errorMessages?.size)
        assertEquals("Customer with id $nonExistingId does not exist", response.body?.errorMessages?.get(0))
    }
//
//    @Test
//    fun shouldSaveNewCustomer() {
//        val customer = Customer(
//                firstName = "Jane",
//                lastName = "Doe",
//                gender = "female",
//                address = "Magic Street 2",
//                phone = "+49 1111 22 33"
//        )
//
//        val returnedCustomerId = customerService.save(customer)
//
//        Assertions.assertNotNull(returnedCustomerId)
//    }
//
//    @Test
//    fun shouldUpdateNewCustomer() {
//        val customerEntity = CustomerEntity(
//                firstName = "Marc",
//                lastName = "Man",
//                gender = "male",
//                address = "Super Street 1",
//                phone = "+49 1111 222 33 44"
//        )
//
//        val savedCustomer = customerRepository.saveAndFlush(customerEntity)
//
//        val customerToUpdate = Customer(
//                id = savedCustomer.id,
//                firstName = "Marco",
//                lastName = "Pollo",
//                gender = "male",
//                address = "Regular Street 1",
//                phone = "+49 1111 222 33 45"
//        )
//
//        val savedUpdatedCustomer = customerService.save(customerToUpdate)
//        val foundUpdatedCustomer = savedCustomer.id?.let {
//            customerService.findById(it)
//        }
//
//        Assertions.assertNotNull(savedUpdatedCustomer.id)
//        Assertions.assertNotNull(foundUpdatedCustomer?.id)
//
//        Assertions.assertEquals(customerToUpdate, savedUpdatedCustomer)
//        Assertions.assertEquals(customerToUpdate, foundUpdatedCustomer)
//    }
//
//    @Test
//    fun shouldDeleteNewCustomer() {
//        val customerEntity = CustomerEntity(
//                firstName = "Ann",
//                lastName = "Lady",
//                gender = "female",
//                address = "Awesome Street 100",
//                phone = "+1 222 333 44 55"
//        )
//
//        val savedCustomer = customerRepository.saveAndFlush(customerEntity)
//
//        val deletedCustomer = savedCustomer.id?.let {
//            customerRepository.deleteById(it)
//            customerService.findById(it)
//        }
//
//        Assertions.assertNotNull(savedCustomer.id)
//        Assertions.assertNull(deletedCustomer)
//    }
//
//
//    private fun addToList(id: Long?, list: ArrayList<Long>) {
//        id?.let {
//            list.add(it)
//        }
//    }
}
