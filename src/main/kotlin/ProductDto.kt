data class ProductDto(
    val id: Int? = null,
    val name: String,
    // Macronutrients with defaults
    val calories: Double = 0.0,
    val carbohydrates: Double = 0.0,
    val sugars: Double = 0.0,
    val fiber: Double = 0.0,
    val protein: Double = 0.0,
    val fat: Double = 0.0,
    val saturatedFat: Double = 0.0,
    val unsaturatedFat: Double? = null,
    val transFat: Double = 0.0,
    // Micronutrients with defaults
    val sodium: Double = 0.0,
    val potassium: Double = 0.0,
    val calcium: Double = 0.0,
    val iron: Double = 0.0,
    val vitaminA: Double = 0.0,
    val vitaminC: Double = 0.0,
    val vitaminD: Double = 0.0,
    val vitaminB12: Double = 0.0,
    val magnesium: Double = 0.0,
    val zinc: Double = 0.0,
    val cholesterol: Double = 0.0,
    val waterContent: Double = 0.0
)