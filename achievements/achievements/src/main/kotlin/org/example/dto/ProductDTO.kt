package org.example.dto

import kotlinx.serialization.Serializable
import org.example.enums.Affiliation

@Serializable
data class ProductDTO(
    val id: Long? = null,
    val name: String,
    val affiliation: Affiliation,
    val water: Double? = null,
    val mass: Double? = null,

    val proteins: Double? = null,

    val fiber: Double? = null,
    val sugar: Double? = null,
    val addedSugar: Double? = null,

    val saturatedFat: Double? = null,
    val polyunsaturatedFat: Double? = null,

    val cholesterol: Double? = null,
    val salt: Double? = null,
    val alcohol: Double? = null,

    val vitaminB7: Double? = null,
    val vitaminC: Double? = null,
    val vitaminD: Double? = null,
    val vitaminE: Double? = null,
    val vitaminK: Double? = null,

    val calcium: Double? = null,
    val iron: Double? = null,
    val zinc: Double? = null,
    
    val kbzhu: Kbzhu? = null
)
