package commerce.hosinsa.domain.product.dto

class ProductResponse(
    val productId: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val brand: String
)
