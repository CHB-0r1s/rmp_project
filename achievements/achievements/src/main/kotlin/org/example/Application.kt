package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.example.routers.configureAchievementRouting

fun main() {
    embeddedServer(Netty, port = 5023, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    configureAchievementRouting()
}
