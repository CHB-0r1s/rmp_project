import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable("products") {
    val name = varchar("name", 255)
    // Macronutrients
    val calories = double("calories")
    val carbohydrates = double("carbohydrates")
    val sugars = double("sugars")
    val fiber = double("fiber")
    val protein = double("protein")
    val fat = double("fat")
    val saturatedFat = double("saturated_fat")
    val unsaturatedFat = double("unsaturated_fat").nullable()
    val transFat = double("trans_fat")
    // Micronutrients
    val sodium = double("sodium")
    val potassium = double("potassium")
    val calcium = double("calcium")
    val iron = double("iron")
    val vitaminA = double("vitamin_a")
    val vitaminC = double("vitamin_c")
    val vitaminD = double("vitamin_d")
    val vitaminB12 = double("vitamin_b12")
    val magnesium = double("magnesium")
    val zinc = double("zinc")
    val cholesterol = double("cholesterol")
    val waterContent = double("water_content")
}