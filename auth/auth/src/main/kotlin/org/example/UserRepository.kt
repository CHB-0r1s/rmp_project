package org.example

import org.example.models.User
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant

object UserRepository {

    // Создание пользователя с расширенными полями
    fun createUser(username: String, password: String, name: String, profilePicturePath: String? = null): Boolean {
        val sql = """
            INSERT INTO users (username, password, name, profile_picture_path, created_at) 
            VALUES (?, ?, ?, ?, ?)
        """.trimIndent()

        Database.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement: PreparedStatement ->
                statement.setString(1, username)
                statement.setString(2, password) // Пароль должен быть уже захэширован
                statement.setString(3, name)
                statement.setString(4, profilePicturePath)
                statement.setTimestamp(5, Timestamp.from(Instant.now()))

                return statement.executeUpdate() > 0
            }
        }
    }

    // Поиск пользователя по username
    fun findUserByUsername(username: String): User? {
        val sql = "SELECT * FROM users WHERE username = ?"
        Database.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement: PreparedStatement ->
                statement.setString(1, username)
                val resultSet: ResultSet = statement.executeQuery()

                return if (resultSet.next()) {
                    User(
                        username = resultSet.getString("username"),
                        password = resultSet.getString("password"),
                        name = resultSet.getString("name"),
                        profilePicturePath = resultSet.getString("profile_picture_path")
                    )
                } else {
                    null
                }
            }
        }
    }

    // Обновление данных пользователя
    fun updateUser(username: String, newName: String? = null, newProfilePicturePath: String? = null): Boolean {
        val updates = mutableListOf<String>()
        val params = mutableListOf<Any>()

        newName?.let {
            updates.add("name = ?")
            params.add(it)
        }

        newProfilePicturePath?.let {
            updates.add("profile_picture_path = ?")
            params.add(it)
        }

        if (updates.isEmpty()) return false

        params.add(username)
        val sql = "UPDATE users SET ${updates.joinToString(", ")} WHERE username = ?"

        Database.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement: PreparedStatement ->
                params.forEachIndexed { index, param ->
                    when (param) {
                        is String -> statement.setString(index + 1, param)
                        else -> throw IllegalArgumentException("Unsupported parameter type")
                    }
                }

                return statement.executeUpdate() > 0
            }
        }
    }

    // Удаление пользователя
    fun deleteUser(username: String): Boolean {
        val sql = "DELETE FROM users WHERE username = ?"
        Database.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement: PreparedStatement ->
                statement.setString(1, username)
                return statement.executeUpdate() > 0
            }
        }
    }

    // Проверка существования пользователя
    fun existsByUsername(username: String): Boolean {
        val sql = "SELECT COUNT(*) FROM users WHERE username = ?"
        Database.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement: PreparedStatement ->
                statement.setString(1, username)
                val resultSet = statement.executeQuery()
                resultSet.next()
                return resultSet.getInt(1) > 0
            }
        }
    }

    // Обновление пароля
    fun updatePassword(username: String, newPassword: String): Boolean {
        val sql = "UPDATE users SET password = ? WHERE username = ?"
        Database.getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement: PreparedStatement ->
                statement.setString(1, newPassword) // Новый пароль должен быть захэширован
                statement.setString(2, username)
                return statement.executeUpdate() > 0
            }
        }
    }
}