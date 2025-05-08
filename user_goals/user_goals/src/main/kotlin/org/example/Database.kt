package org.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object Database {
    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://db:5432/mobile"
            username = System.getenv("DB_USER") ?: "postgres"
            password = System.getenv("DB_PASSWORD") ?: "password"
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 10
        }

        dataSource = HikariDataSource(config)
    }

    fun getConnection(): Connection = dataSource.connection
}
