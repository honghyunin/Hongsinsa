package commerce.hongsinsa.domain.dto.product

class ProductResponse(
    val productId: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val brand: String,
    var options: MutableList<ProductOptionResponse>
)