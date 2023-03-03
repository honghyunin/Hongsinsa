package commerce.hosinsa.domain.dto.order

class OrderRequestDto(
    val quantity: Byte,
    val address: String,
    val name: String,
    val phoneNumber: String,
    val deliveryInstructions: String?,
    val productIdxList: MutableList<Int>,
    val productQuantities: Map<Int, Byte>
)