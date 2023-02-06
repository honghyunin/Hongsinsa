package commerce.hosinsa.domain.product.dto

class UpdateProductDto(
    val productId: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val stock: Int
)
