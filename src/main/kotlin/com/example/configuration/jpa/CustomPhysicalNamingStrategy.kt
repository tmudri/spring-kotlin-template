package com.example.configuration.jpa

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment

class CustomPhysicalNamingStrategy : PhysicalNamingStrategy {

    override fun toPhysicalColumnName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? = convertNullable(name, true)

    override fun toPhysicalTableName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? = convertNullable(name, true)

    override fun toPhysicalSchemaName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? = convertNullable(name)

    override fun toPhysicalCatalogName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? = convertNullable(name)

    override fun toPhysicalSequenceName(name: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? = Identifier(name?.text, true) // convertNullable(name, true)

    private fun convertNullable(name: Identifier?, isWithQuotes: Boolean = false) =
            if (name == null) {
                name
            } else {
                convertToUpperSnakeCase(name, isWithQuotes)
            }

    private fun convertToUpperSnakeCase(identifier: Identifier, isWithQuotes: Boolean): Identifier {
        val regex = "([a-z])([A-Z])"
        val replacement = "$1_$2"
        val snakeCaseName = identifier
                .text
                .replace(regex.toRegex(), replacement)
                .toUpperCase()
                .withQuotes(isWithQuotes)

        return Identifier.toIdentifier(snakeCaseName)
    }
}

fun String.withQuotes(isWithQuotes: Boolean): String =
        if (isWithQuotes) {
            "\"$this\""
        } else {
            this
        }
