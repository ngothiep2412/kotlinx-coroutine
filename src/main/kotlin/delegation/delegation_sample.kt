package delegation

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
)

interface ProductRepository {
    fun getFavoriteProducts(): List<Product>
    fun removeFromFavorite(): List<Product>
    fun findById(): List<Product>
    fun getPopularProducts(): List<Product>
}

fun provideProductRepository(): ProductRepository = ProductRepositoryV1()

// api/1.x or api/v1
private class ProductRepositoryV1: ProductRepository {
    override fun getFavoriteProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun removeFromFavorite(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun findById(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getPopularProducts(): List<Product> {
        TODO("Not yet implemented")
    }

}