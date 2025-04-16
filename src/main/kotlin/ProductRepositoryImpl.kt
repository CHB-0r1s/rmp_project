import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepositoryImpl : ProductRepository {
    private fun Product.toDto() = ProductDto(
        id = id.value,
        name = name,
        calories = calories,
        carbohydrates = carbohydrates,
        sugars = sugars,
        fiber = fiber,
        protein = protein,
        fat = fat,
        saturatedFat = saturatedFat,
        unsaturatedFat = unsaturatedFat,
        transFat = transFat,
        sodium = sodium,
        potassium = potassium,
        calcium = calcium,
        iron = iron,
        vitaminA = vitaminA,
        vitaminC = vitaminC,
        vitaminD = vitaminD,
        vitaminB12 = vitaminB12,
        magnesium = magnesium,
        zinc = zinc,
        cholesterol = cholesterol,
        waterContent = waterContent
    )

    private fun Product.updateFrom(dto: ProductDto) {
        name = dto.name
        calories = dto.calories
        carbohydrates = dto.carbohydrates
        sugars = dto.sugars
        fiber = dto.fiber
        protein = dto.protein
        fat = dto.fat
        saturatedFat = dto.saturatedFat
        unsaturatedFat = dto.unsaturatedFat
        transFat = dto.transFat
        sodium = dto.sodium
        potassium = dto.potassium
        calcium = dto.calcium
        iron = dto.iron
        vitaminA = dto.vitaminA
        vitaminC = dto.vitaminC
        vitaminD = dto.vitaminD
        vitaminB12 = dto.vitaminB12
        magnesium = dto.magnesium
        zinc = dto.zinc
        cholesterol = dto.cholesterol
        waterContent = dto.waterContent
    }

    override fun create(product: ProductDto): ProductDto = transaction {
        Product.new {
            updateFrom(product)
        }.toDto()
    }

    override fun findById(id: Int): ProductDto? = transaction {
        Product.findById(id)?.toDto()
    }

    override fun findAll(): List<ProductDto> = transaction {
        Product.all().map { it.toDto() }
    }

    override fun update(product: ProductDto): ProductDto = transaction {
        require(product.id != null) { "Product ID must not be null for update" }

        val existing = Product.findById(product.id)
            ?: throw IllegalArgumentException("Product with id ${product.id} not found")

        existing.updateFrom(product)
        return@transaction existing.toDto()
    }

    override fun delete(id: Int) = transaction {
        Product.findById(id)?.delete()
            ?: throw IllegalArgumentException("Product with id $id not found")
    }

    override fun findByName(name: String): ProductDto? = transaction {
        Product.find { Products.name eq name }.firstOrNull()?.toDto()
    }

    override fun findByCaloriesRange(min: Double, max: Double): List<ProductDto> = transaction {
        Product.find { Products.calories.between(min, max) }.map { it.toDto() }
    }
}