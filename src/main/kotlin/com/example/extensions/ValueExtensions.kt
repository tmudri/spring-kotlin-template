package com.example.extensions

import java.util.Enumeration

fun <T> T?.whenNull(defaultValue: T): T = this?.let { return@let it } ?: defaultValue

fun <T> Collection<T>.toEnumeration() =
        object : Enumeration<T> {

            private val iterator = this@toEnumeration.iterator()

            override fun hasMoreElements() = iterator.hasNext()

            override fun nextElement() = iterator.next()
        }
