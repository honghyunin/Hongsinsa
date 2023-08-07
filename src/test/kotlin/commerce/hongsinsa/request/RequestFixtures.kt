package commerce.hongsinsa.request

import commerce.hongsinsa.dto.request.GetRequestDto
import commerce.hongsinsa.dto.request.RequestDto
import commerce.hongsinsa.member.MEMBER
import commerce.hongsinsa.product.*
import commerce.hongsinsa.repository.member.MemberRepository
import commerce.hongsinsa.repository.request.RequestProductQueryRepository
import commerce.hongsinsa.repository.request.RequestProductRepository
import commerce.hongsinsa.repository.request.RequestRepository
import commerce.hongsinsa.repository.product.ProductRepository
import commerce.hongsinsa.service.request.RequestService
import commerce.hongsinsa.entity.request.RequestProduct
import commerce.hongsinsa.entity.request.RequestStatus
import commerce.hongsinsa.entity.product.ProductSize
import commerce.hongsinsa.config.utils.CurrentMemberUtil
import commerce.hongsinsa.extension.toOrder
import io.mockk.mockk
import java.time.LocalDateTime

const val REQUEST_IDX = 1
const val QUANTITY = (2).toByte()
const val ADDRESS = "ADDRESS"
const val NAME = "NAME"
const val PHONE_NUMBER = "010-1234-5678"
const val DELIVERY_INSTRUCTIONS = ""
const val COUNT = (3).toByte()
val PRODUCT_IDX_LIST = mutableListOf(1, 2, 3, 4)
val PRODUCT_QUANTITIES = mutableMapOf(1 to (2).toByte(), 2 to (3).toByte())
val ORDER_STATUS = RequestStatus.Request_RECEIVED

val REQUEST_DTO = RequestDto(
    quantity = QUANTITY,
    address = ADDRESS,
    name = NAME,
    phoneNumber = PHONE_NUMBER,
    deliveryInstructions = DELIVERY_INSTRUCTIONS,
    productIdxList = PRODUCT_IDX_LIST,
    productQuantities = PRODUCT_QUANTITIES,
    color = "검정",
    size = ProductSize.M
)

val EMPTY_PRODUCT_QUANTITIES_REQUEST_DTO = RequestDto(
    quantity = QUANTITY,
    address = ADDRESS,
    name = NAME,
    phoneNumber = PHONE_NUMBER,
    deliveryInstructions = DELIVERY_INSTRUCTIONS,
    productIdxList = PRODUCT_IDX_LIST,
    productQuantities = mutableMapOf(),
    color = "검정",
    size = ProductSize.M
)

val REQUEST = REQUEST_DTO.toOrder(MEMBER)

val REQUEST_PRODUCT = RequestProduct(
    product = PRODUCT,
    request = REQUEST,
    count = COUNT,
    size = PRODUCT_SIZE,
    color = COLOR
)

val REQUEST_RESPONSE = GetRequestDto(
    requestIdx = REQUEST_IDX,
    productName = PRODUCT_NAME,
    brandName = BRAND_NAME,
    productIdx = PRODUCT_IDX,
    requestCreatedAt = LocalDateTime.now(),
    quantity = QUANTITY,
    price = PRICE,
    size = PRODUCT_SIZE,
    color = COLOR,
    status = ORDER_STATUS
)

val REQUEST_PRODUCT_LIST = mutableListOf(REQUEST_PRODUCT, REQUEST_PRODUCT)

val GET_REQUEST_RESPONSE_LIST = mutableListOf(REQUEST_RESPONSE, REQUEST_RESPONSE)

fun deleteOrder(): MutableList<RequestProduct> = requestService.findAllByOrderAndIsDeleteFalse(REQUEST)
    .map { orderProduct ->
        orderProduct.isDelete = true
        orderProduct
    }.toMutableList()

val requestRepository = mockk<RequestRepository>()
val requestProductRepository = mockk<RequestProductRepository>()
val requestProductQueryRepository = mockk<RequestProductQueryRepository>()
val productRepository = mockk<ProductRepository>()
val currentMemberUtil = mockk<CurrentMemberUtil>(relaxed = true)
val memberRepository = mockk<MemberRepository>()
val requestService = mockk<RequestService>()