package org.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.UserGoalsRepository
import org.example.models.*
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("RoutingLogger")

    routing {
        authenticate("auth-jwt") {

            /* CREATE / REPLACE */
            post("/goals") {
                val user = call.user() ?: return@post
                val req  = call.safeBody<CreateGoalRequest>() ?: return@post
                val goal = req.toEntity(user)

                if (UserGoalsRepository.saveGoal(goal))
                    call.respond(HttpStatusCode.Created,
                        GoalCreatedResponse("Goal saved", goal))
                else
                    call.respond(HttpStatusCode.InternalServerError,
                        ErrorResponse("DB error"))
            }

            /* READ */
            get("/goals") {
                val user = call.user() ?: return@get
                val goal = UserGoalsRepository.getGoal(user)
                if (goal == null)
                    call.respond(HttpStatusCode.NotFound,
                        ErrorResponse("No goal set"))
                else
                    call.respond(goal)
            }

            /* UPDATE (same as create) */
            put("/goals") {
                val user = call.user() ?: return@put
                val req  = call.safeBody<CreateGoalRequest>() ?: return@put
                val ok   = UserGoalsRepository.saveGoal(req.toEntity(user))

                if (ok) call.respond(HttpStatusCode.OK,
                    mapOf("message" to "Goal updated"))
                else   call.respond(HttpStatusCode.InternalServerError,
                    ErrorResponse("DB error"))
            }

            /* DELETE */
            delete("/goals") {
                val user = call.user() ?: return@delete
                if (UserGoalsRepository.deleteGoal(user))
                    call.respond(HttpStatusCode.OK,
                        mapOf("message" to "Goal deleted"))
                else
                    call.respond(HttpStatusCode.NotFound,
                        ErrorResponse("No goal to delete"))
            }
        }
    }
}

/* ---------- top‑level helpers ---------- */

private suspend fun ApplicationCall.user(): String? =
    principal<JWTPrincipal>()?.payload?.getClaim("username")?.asString()
        ?: run {
            respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid token"))
            null
        }

private suspend inline fun <reified T : Any> ApplicationCall.safeBody(): T? =
    runCatching { receive<T>() }.getOrElse {
        respond(HttpStatusCode.BadRequest, ErrorResponse("Malformed JSON"))
        null
    }


/* DTO‑>entity */
private fun CreateGoalRequest.toEntity(userId: String) = UserGoal(
    user_id       = userId,
    goal_type     = goal_type,
    activity_level= activity_level,
    weekly_target = weekly_target,
    calorie_goal  = calorie_goal,
    water_goal    = water_goal,
    steps_goal    = steps_goal,
    bju_goal      = bju_goal
)
