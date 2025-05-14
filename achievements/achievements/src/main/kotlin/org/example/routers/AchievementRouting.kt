package org.example.routers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.dto.AchievementResponseDTO
import org.example.dto.AchievementsResponseDTO
import org.example.dto.ErrorResponse
import org.example.enums.Achievement
import org.example.service.AchievementManager

fun Application.configureAchievementRouting() {
    val achievementManager = AchievementManager()

    routing {
        route("/achievements") {
            get("/{achievement}") {
                val achievementName = call.parameters["achievement"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, ErrorResponse("Achievement name is required"))

                val achievement = try {
                    Achievement.valueOf(achievementName.uppercase())
                } catch (e: IllegalArgumentException) {
                    return@get call.respond(HttpStatusCode.NotFound, ErrorResponse("Achievement not found"))
                }

                val username = call.request.header("X-User-ID")
                    ?: return@get call.respond(HttpStatusCode.Unauthorized, ErrorResponse("User ID is required"))

                try {
                    val isUnlocked = achievementManager.isAchievementUnlocked(username, achievement)

                    call.respond(
                        AchievementResponseDTO(
                            title = achievement.title,
                            description = achievement.description,
                            isUnlocked = isUnlocked
                        )
                    )
                } catch (e: IllegalArgumentException) {
                    if (e.message?.contains("User with username") == true) {
                        call.respond(HttpStatusCode.NotFound, ErrorResponse(e.message!!))
                    } else {
                        call.respond(HttpStatusCode.BadRequest, ErrorResponse(e.message!!))
                    }
                }
            }

            get {
                val username = call.request.header("X-User-ID")
                    ?: return@get call.respond(HttpStatusCode.Unauthorized, ErrorResponse("User ID is required"))

                try {
                    val achievementsStatus = achievementManager.getAllAchievementsStatus(username)
                    val achievements = achievementsStatus.mapKeys { it.key.name }.mapValues { (achievementName, isUnlocked) ->
                        val achievement = Achievement.valueOf(achievementName)
                        AchievementResponseDTO(
                            title = achievement.title,
                            description = achievement.description,
                            isUnlocked = isUnlocked
                        )
                    }

                    call.respond(AchievementsResponseDTO(achievements = achievements))
                } catch (e: IllegalArgumentException) {
                    if (e.message?.contains("User with username") == true) {
                        call.respond(HttpStatusCode.NotFound, ErrorResponse(e.message!!))
                    } else {
                        call.respond(HttpStatusCode.BadRequest, ErrorResponse(e.message!!))
                    }
                }
            }
        }
    }
} 