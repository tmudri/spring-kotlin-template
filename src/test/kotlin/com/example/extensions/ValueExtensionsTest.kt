package com.example.extensions

import java.lang.IllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

private const val NOT_NULL_STRING = "Not null"
private const val ELEMENT_ONE = "One"

class ValueExtensionsTest {

    @Test
    fun shouldReturnDefaultValueWhenNull() {
        val nullableValue: String? = null

        val nonNullableValue = nullableValue.whenNull(NOT_NULL_STRING)

        assertNotNull(nonNullableValue)
        assertEquals(NOT_NULL_STRING, nonNullableValue)
    }

    @Test
    fun shouldReturnOriginalValueWhenNull() {
        val nullableValue: String? = "Original"

        val nonNullableValue = nullableValue.whenNull(NOT_NULL_STRING)

        assertNotNull(nonNullableValue)
        assertEquals(nullableValue!!, nonNullableValue)
    }

    @Test
    fun shouldReturnNextElementOnceWhenHasOneElement() {
        val list = listOf(ELEMENT_ONE)

        val enumeration = list.toEnumeration()

        assertEquals(ELEMENT_ONE, enumeration.nextElement())
    }

    @Test
    fun shouldReturnNextElementErrorWhenHasNoElements() {
        val list = emptyList<String>()

        val enumeration = list.toEnumeration()

        assertThrows(NoSuchElementException::class.java) {
            enumeration.nextElement()
        }
    }

    @Test
    fun shouldReturnHasElementsTrueWhenHasElements() {
        val list = listOf(ELEMENT_ONE)

        val enumeration = list.toEnumeration()

        assertTrue(enumeration.hasMoreElements())
    }

    @Test
    fun shouldReturnHasElementsFalseWhenHasNoElements() {
        val list = emptyList<String>()

        val enumeration = list.toEnumeration()

        assertFalse(enumeration.hasMoreElements())
    }

    @Test
    fun shouldReturnNonNullableValueWhenThrowIfNullIsCalledWithValue() {
        val s: String? = "String"

        assertDoesNotThrow {
            s.throwIfNull(IllegalArgumentException())
        }
    }

    @Test
    fun shouldThrowProvidedExceptionWhenThrowIfNullIsCalledWithValue() {
        val s: String? = null

        assertThrows(IllegalArgumentException::class.java) {
            s.throwIfNull(IllegalArgumentException())
        }
    }
}
