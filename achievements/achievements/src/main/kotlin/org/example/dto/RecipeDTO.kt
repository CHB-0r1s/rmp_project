package org.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val id: Long? = null,
    val name: String,
    val username: String,
    val products: List<ProductDTO>
)

@Serializable
data class RecipeProductDTO(
    val recipeId: Long,
    val productId: Long
)