interface ProductRepository {
    // CRUD Operations
    fun create(product: ProductDto): ProductDto
    fun findById(id: Int): ProductDto?
    fun findAll(): List<ProductDto>
    fun update(product: ProductDto): ProductDto
    fun delete(id: Int)

    // Query Methods
    fun findByName(name: String): ProductDto?
    fun findByCaloriesRange(min: Double, max: Double): List<ProductDto>

    companion object {
        operator fun invoke(): ProductRepository = ProductRepositoryImpl()
    }
}