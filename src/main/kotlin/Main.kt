fun main() {
    // 1. Initialize Database
    println("⏳ Initializing database...")
    DatabaseConfig.init()
    println("✅ Database connected successfully\n")

    val productRepository = ProductRepository()

    // 2. Test Create
    println("🧪 Testing CREATE operation...")
    val testProduct = ProductDto(
        name = "Banana",
        calories = 89.0,
        carbohydrates = 22.8,
        sugars = 12.2,
        fiber = 2.6,
        potassium = 358.0,
        vitaminC = 8.7
    )

    val createdProduct = productRepository.create(testProduct)
    println("✅ Created product: ${createdProduct.name} (ID: ${createdProduct.id})")
    println("   Details: ${createdProduct.calories}kcal, ${createdProduct.vitaminC}mg Vitamin C\n")

    // 3. Test FindById
    println("🔍 Testing FIND BY ID...")
    val foundProduct = createdProduct.id?.let { productRepository.findById(it) }
    println(foundProduct?.let {
        "✅ Found product: ${it.name} (Carbs: ${it.carbohydrates}g)"
    } ?: "❌ Product not found")
    println()

    // 4. Test FindByName
    println("🔎 Testing FIND BY NAME...")
    val bananaSearch = productRepository.findByName("Bananna") // Intentional typo
    val bananaFound = productRepository.findByName("Banana")

    println(bananaSearch?.let {
        "❌ Unexpectedly found: ${it.name}"
    } ?: "✅ Correctly didn't find 'Banana' with typo")

    println(bananaFound?.let {
        "✅ Found: ${it.name} (Potassium: ${it.potassium}mg)"
    } ?: "❌ Failed to find existing Banana")
    println()

    // 5. Test FindAll
    println("📋 Testing FIND ALL...")
    productRepository.create(ProductDto(name = "Apple", calories = 52.0, vitaminC = 8.4))
    productRepository.create(ProductDto(name = "Spinach", calories = 23.0, iron = 2.7))

    val allProducts = productRepository.findAll()
    println("✅ Found ${allProducts.size} products:")
    allProducts.forEach {
        println("   - ${it.name.padEnd(8)} (ID: ${it.id}, Cals: ${it.calories}kcal)")
    }
    println()

    // 6. Test FindByCaloriesRange
    println("⚖️ Testing CALORIE RANGE SEARCH (0-100 kcal)...")
    val lowCalorieFoods = productRepository.findByCaloriesRange(0.0, 100.0)
    println("✅ Found ${lowCalorieFoods.size} low-calorie foods:")
    lowCalorieFoods.forEach {
        println("   - ${it.name.padEnd(8)} (${it.calories}kcal)")
    }
    println()

    // 7. Test Update
    println("🔄 Testing UPDATE...")
    val updatedBanana = bananaFound?.copy(
        calories = 105.0,  // New research shows bananas have more calories
        fiber = 3.1
    )?.let { productRepository.update(it) }

    println(updatedBanana?.let {
        "✅ Updated: ${it.name} (New values: ${it.calories}kcal, Fiber: ${it.fiber}g)"
    } ?: "❌ Update failed")
    println()

    // 8. Test Delete
    println("🗑️ Testing DELETE...")
    updatedBanana?.id?.let { id ->
        productRepository.delete(id)
        println("✅ Deleted product ID $id")
        println("   Verification: ${productRepository.findById(id)?.let { "❌ Still exists!" } ?: "✅ Confirmed deleted"}")
    }
    println()

    // Final state
    println("🏁 Final database state:")
    productRepository.findAll().forEachIndexed { index, product ->
        println("${index + 1}. ${product.name} (ID: ${product.id})")
    }
}