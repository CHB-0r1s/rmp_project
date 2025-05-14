package org.example.models

data class UpdateGenderRequest(val gender: String) // Пол
data class UpdateWeightRequest(val weight: Float) // Вес
data class UpdateHeightRequest(val height: Float) // Рост
data class UpdateWaistRequest(val waist: Float) // Талия
data class UpdateHipsRequest(val hips: Float) // Бёдра
data class UpdateChestRequest(val chest: Float) // Грудь
data class UpdateArmsRequest(val arms: Float) // Руки
data class UpdateBodyFatRequest(val bodyFat: Float) // Объём жира
data class UpdateMuscleMassRequest(val muscleMass: Float) // Мышечная масса
data class UpdateBloodGlucoseRequest(val bloodGlucose: Float) // Глюкоза в крови
data class UpdateBloodPressureSystolicRequest(val systolic: Int) // Давление систолическое
data class UpdateBloodPressureDiastolicRequest(val diastolic: Int) // Давление диастолическое

data class ErrorResponse(val message: String)
data class MeasurementsResponse(
    val gender: String,
    val weight: Float,
    val height: Float,
    val waist: Float,
    val hips: Float,
    val chest: Float,
    val arms: Float,
    val bodyFat: Float,
    val muscleMass: Float,
    val bloodGlucose: Float,
    val bloodPressureSystolic: Int,
    val bloodPressureDiastolic: Int,
    val measuredAt: String
)

