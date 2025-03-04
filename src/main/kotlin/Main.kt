import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database as ExposedDatabase

fun main() {
    // Connect to PostgreSQL using Exposed
    ExposedDatabase.connect(
        "jdbc:postgresql://localhost:5432/studs",
        driver = "org.postgresql.Driver",
        user = "user",
        password = "password"
    )

    // Create the table and insert a user
    transaction {
        SchemaUtils.create(Users) // This creates the table if it doesn't exist

        // Insert a new user
        val newUser = User.new {
            name = "Alice3"
            email = "alice3@example.com"
        }

        println("Inserted User: $newUser")
    }

    // Retrieve and print all users
    transaction {
        println("Users in Database:")
        User.all().forEach { println(it) }
    }
}

