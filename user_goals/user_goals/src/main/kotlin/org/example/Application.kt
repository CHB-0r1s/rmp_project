package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.response.*
import org.example.plugins.configureRouting
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 5015, host = "0.0.0.0") { module() }
        .start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    // ✅ Properly configure Content Negotiation with Kotlinx JSON
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    // 🔐 Install the Authentication plugin
    install(Authentication) {
        jwt("auth-jwt") {
            val secret = System.getenv("JWT_SECRET") ?: "mysecret"
            val issuer = System.getenv("JWT_ISSUER") ?: "http://0.0.0.0:5000"
            val audience = System.getenv("JWT_AUDIENCE") ?: "http://0.0.0.0:5000"
            val algorithm = Algorithm.HMAC256(secret)

            realm = "Access to user goals"
            verifier(
                JWT
                    .require(algorithm)
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(
                    io.ktor.http.HttpStatusCode.Unauthorized,
                    "Invalid or expired token"
                )
            }
        }
    }

    // Load routes
    configureRouting()
}
