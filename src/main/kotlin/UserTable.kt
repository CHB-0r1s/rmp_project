import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// Define the table (Schema)
object Users : IntIdTable() {
    val name = varchar("name", 255)
    val email = varchar("email", 255).uniqueIndex()
}

// Define a Data Access Object (DAO)
class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var email by Users.email

    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email')"
    }
}
