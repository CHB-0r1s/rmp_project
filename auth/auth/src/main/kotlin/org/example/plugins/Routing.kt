package org.example.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.auth.jwt.*
import org.example.models.*
import java.security.MessageDigest
import java.io.File
import java.util.*
import org.example.utils.PasswordHasher
import org.example.utils.ImageUtils
import org.example.UserRepository

fun Application.configureRouting() {
    val secret = System.getenv("JWT_SECRET") ?: throw IllegalStateException("JWT_SECRET not found")
    val issuer = System.getenv("JWT_ISSUER") ?: throw IllegalStateException("JWT_ISSUER not found")
    val audience = System.getenv("JWT_AUDIENCE") ?: throw IllegalStateException("JWT_AUDIENCE not found")
    val algorithm = Algorithm.HMAC256(secret)

    routing {
        post("/register") {
            val registerRequest = call.receive<RegisterRequest>()

            if (registerRequest.username.length < 3) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Username must be at least 3 characters"))
                return@post
            }

            if (registerRequest.password.length < 6) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Password must be at least 6 characters"))
                return@post
            }

            if (registerRequest.name.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Name cannot be empty"))
                return@post
            }

            val userExists = UserRepository.findUserByUsername(registerRequest.username) != null
            if (userExists) {
                call.respond(HttpStatusCode.Conflict, ErrorResponse("Username already exists"))
                return@post
            }

            val hashedPassword = PasswordHasher.hash(registerRequest.password)

            val profilePicturePath = registerRequest.profilePictureBase64?.let {
                try {
                    ImageUtils.saveBase64Image(it, registerRequest.username)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse("Invalid image: ${e.message}"))
                    return@post
                }
            }

            if (UserRepository.createUser(
                    registerRequest.username,
                    hashedPassword,
                    registerRequest.name,
                    profilePicturePath
                )) {
                call.respond(HttpStatusCode.Created, mapOf("message" to "User registered successfully"))
            } else {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to register user"))
            }
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val foundUser = UserRepository.findUserByUsername(loginRequest.username)
            if (foundUser != null && foundUser.password == PasswordHasher.hash(loginRequest.password)) {
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("username", foundUser.username)
                    .sign(algorithm)
                call.respond(HttpStatusCode.OK, AuthResponse(token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid username or password"))
            }
        }

        authenticate("auth-jwt") {
            get("/validate") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                if (username != null) {
                    call.response.header("X-User-ID", username)
                    call.respond(HttpStatusCode.OK, ValidateResponse(username, "Token is valid"))
                } else {
                    call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid token"))
                }
            }

            get("/profile") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                    ?: throw IllegalStateException("Username not found in token")

                val user = UserRepository.findUserByUsername(username)
                    ?: throw IllegalStateException("User not found")

                call.respond(mapOf(
                    "username" to user.username,
                    "name" to user.name,
                    "profilePictureUrl" to user.profilePicturePath?.let { "/profile/image/$it" }
                ))
            }

            put("/profile") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                    ?: throw IllegalStateException("Username not found in token")

                val updateRequest = call.receive<UpdateProfileRequest>()

                val newProfilePicturePath = updateRequest.profilePictureBase64?.let {
                    ImageUtils.saveBase64Image(it, username)
                }

                if (UserRepository.updateUser(
                        username,
                        updateRequest.name,
                        newProfilePicturePath
                    )) {
                    call.respond(HttpStatusCode.OK, mapOf(
                        "message" to "Profile updated successfully",
                        "profilePictureUrl" to newProfilePicturePath?.let { "/profile/image/$it" }
                    ))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update profile"))
                }
            }

            get("/profile/image/{path}") {
                val path = call.parameters["path"]
                    ?: throw IllegalArgumentException("Path parameter is missing")

                val imageFile = ImageUtils.getProfileImage(path)
                if (imageFile.exists()) {
                    call.response.header(HttpHeaders.ContentType, "image/jpeg")
                    call.respond(imageFile.readBytes())
                } else {
                    call.respond(HttpStatusCode.NotFound, ErrorResponse("Image not found"))
                }
            }
        }
    }
}