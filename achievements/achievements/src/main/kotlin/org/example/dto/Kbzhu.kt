package org.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kbzhu(
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
) 