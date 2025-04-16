import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Product(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Product>(Products)

    var name by Products.name
    // Macronutrients
    var calories by Products.calories
    var carbohydrates by Products.carbohydrates
    var sugars by Products.sugars
    var fiber by Products.fiber
    var protein by Products.protein
    var fat by Products.fat
    var saturatedFat by Products.saturatedFat
    var unsaturatedFat by Products.unsaturatedFat
    var transFat by Products.transFat
    // Micronutrients
    var sodium by Products.sodium
    var potassium by Products.potassium
    var calcium by Products.calcium
    var iron by Products.iron
    var vitaminA by Products.vitaminA
    var vitaminC by Products.vitaminC
    var vitaminD by Products.vitaminD
    var vitaminB12 by Products.vitaminB12
    var magnesium by Products.magnesium
    var zinc by Products.zinc
    var cholesterol by Products.cholesterol
    var waterContent by Products.waterContent
}