package commerce.hongsinsa.dto.request

import commerce.hongsinsa.entity.product.ProductSize

class RequestDto(
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