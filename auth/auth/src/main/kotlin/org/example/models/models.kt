package org.example.models

data class RegisterRequest(
    val username: String,       // Никнейм (уникальный идентификатор)
    val password: String,      // Пароль
    val name: String,          // Реальное имя пользователя
    val profilePictureBase64: String? = null  // Фото в формате base64 (опционально)
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val token: String
)

data class ErrorResponse(
    val message: String
)

data class ValidateResponse(
    val username: String,
    val message: String = "Token is valid"
)

data class User(
    val username: String,
    val password: String,
    val name: String,
    val profilePicturePath: String? = null  // Путь к файлу на сервере
)

data class UpdateProfileRequest(
    val name: String? = null,
    val profilePictureBase64: String? = null
)