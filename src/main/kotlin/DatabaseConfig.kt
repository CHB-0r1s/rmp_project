import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object DatabaseConfig {
    fun init() {
    //from server
//        val database = Database.connect(
//            "jdbc:postgresql://localhost:5432/studs", // PostgreSQL URL
//            driver = "org.postgresql.Driver",         // PostgreSQL driver
//            user = "user",
//            password = "password"
//        )
    //local
        val database = Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres", // default DB name
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "" // leave blank if still using 'trust' in pg_hba.conf
        )

        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Products)
        }
    }
}
