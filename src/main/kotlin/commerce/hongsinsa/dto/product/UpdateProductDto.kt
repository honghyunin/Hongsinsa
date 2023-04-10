package commerce.hongsinsa.dto.product

class UpdateProductDto(
    val productIdx: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val stock: Int
)
