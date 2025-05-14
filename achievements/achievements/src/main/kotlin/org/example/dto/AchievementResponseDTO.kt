package org.example.dto

import kotlinx.serialization.Serializable
import org.example.enums.Achievement

@Serializable
data class AchievementResponseDTO(
    val title: String,
    val description: String,
    val isUnlocked: Boolean
)

@Serializable
data class AchievementsResponseDTO(
    val achievements: Map<String, AchievementResponseDTO>
) 