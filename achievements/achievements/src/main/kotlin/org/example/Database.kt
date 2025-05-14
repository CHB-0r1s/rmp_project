package org.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object Database {
    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = System.getenv("DB_URL") ?: throw IllegalStateException("DB_URL not found in environment variables")
            username = System.getenv("DB_USER") ?: throw IllegalStateException("DB_USER not found in environment variables")
            password = System.getenv("DB_PASSWORD") ?: throw IllegalStateException("DB_PASSWORD not found in environment variables")
            maximumPoolSize = 10
            minimumIdle = 2
            idleTimeout = 30000
            maxLifetime = 1800000
            connectionTimeout = 30000
        }
        dataSource = HikariDataSource(config)
    }

    fun getConnection(): Connection {
        return dataSource.connection
    }
}