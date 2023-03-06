package commerce.hosinsa.domain.dto.product

import commerce.hosinsa.entity.product.ProductSize

class RegistrationProductDto(
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val stock: Int,
    val brandName: String,
    val color: MutableSet<String>,
    val size: MutableSet<ProductSize>
)
