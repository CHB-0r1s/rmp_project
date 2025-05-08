package org.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateGoalRequest(
    val goal_type: String,
    val activity_level: String,
    val weekly_target: Double,
    val calorie_goal: Int,
    val water_goal: Int,
    val steps_goal: Int,
    val bju_goal: String
)

@Serializable              // one row per user, PK = user_id
data class UserGoal(
    val user_id: String,
    val goal_type: String,
    val activity_level: String,
    val weekly_target: Double,
    val calorie_goal: Int,
    val water_goal: Int,
    val steps_goal: Int,
    val bju_goal: String
)

@Serializable
data class ErrorResponse(val message: String)

@Serializable
data class GoalCreatedResponse(val message: String, val goal: UserGoal)
