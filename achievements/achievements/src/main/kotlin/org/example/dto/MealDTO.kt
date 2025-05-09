package org.example.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.example.enums.TypesMeals
import org.example.plugins.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class MealDTO(
    val id: Long,
    val type: TypesMeals,
    @Serializable(with = LocalDateTimeSerializer::class)
    val addedAt: LocalDateTime,
    val username: String,
    val products: List<ProductDTO>,
    val recipes: List<RecipeDTO>
)

@Serializable
data class MealRequestDTO(
    val type: TypesMeals,
    val productIds: List<Long>,
    val recipeIds: List<Long>
) 