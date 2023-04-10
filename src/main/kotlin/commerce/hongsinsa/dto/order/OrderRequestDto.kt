package commerce.hongsinsa.dto.order

import commerce.hongsinsa.entity.product.ProductSize

class OrderRequestDto(
    val quantity: Byte,
    val address: String,
    val name: String,
    val phoneNumber: String,
    val deliveryInstructions: String?,
    val productIdxList: MutableList<Int>,
    val productQuantities: MutableMap<Int, Byte>,
    val color: String,
    val size: ProductSize
)