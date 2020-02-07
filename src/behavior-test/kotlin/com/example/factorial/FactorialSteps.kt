package com.example.factorial

import com.example.factorial.domain.Factorial
import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FactorialSteps : En {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    var url: String? = null
    var factorialOf: Int? = null
    var response: ResponseEntity<Factorial>? = null

    init {
        Given("user needs to calculate factorial number") {
            url = "/api/v1/factorial/"
        }

        When<Int>("user calls endpoint with number {int}") { requestNumber ->
            factorialOf = requestNumber
            response = restTemplate.getForEntity<Factorial>(url + requestNumber)
        }

        Then<Int>("service returns calculated factorial number equal to {int}") { calculatedFactorialNumber ->
            Assertions.assertEquals(HttpStatus.OK, response?.statusCode)
            Assertions.assertEquals(Factorial(factorialOf ?: 0, calculatedFactorialNumber), response?.body)
        }
    }
}
