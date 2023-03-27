package commerce.hongsinsa.domain.order

import commerce.hongsinsa.domain.dto.order.GetOrderDto
import commerce.hongsinsa.domain.dto.order.OrderRequestDto
import commerce.hongsinsa.domain.member.MEMBER
import commerce.hongsinsa.domain.product.*
import commerce.hongsinsa.domain.repository.member.MemberRepository
import commerce.hongsinsa.domain.repository.order.OrderProductCustomRepository
import commerce.hongsinsa.domain.repository.order.OrderProductRepository
import commerce.hongsinsa.domain.repository.order.OrderRepository
import commerce.hongsinsa.domain.repository.product.ProductRepository
import commerce.hongsinsa.domain.service.OrderService
import commerce.hongsinsa.entity.order.OrderProduct
import commerce.hongsinsa.entity.order.OrderStatus
import commerce.hongsinsa.entity.product.ProductSize
import commerce.hongsinsa.global.config.utils.CurrentMemberUtil
import commerce.hongsinsa.global.extension.toOrder
import io.mockk.mockk
import java.time.LocalDateTime

const val ORDER_IDX = 1
const val QUANTITY = (2).toByte()
const val ADDRESS = "ADDRESS"
const val NAME = "NAME"
const val PHONE_NUMBER = "010-1234-5678"
const val DELIVERY_INSTRUCTIONS = ""
const val COUNT = (3).toByte()
val PRODUCT_IDX_LIST = mutableListOf(1, 2, 3, 4)
val PRODUCT_QUANTITIES = mutableMapOf(1 to (2).toByte(), 2 to (3).toByte())
val ORDER_STATUS = OrderStatus.ORDER_RECEIVED

val ORDER_REQUEST_DTO = OrderRequestDto(
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

val EMPTY_PRODUCT_QUANTITIES_ORDER_REQUEST_DTO = OrderRequestDto(
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

val ORDER = ORDER_REQUEST_DTO.toOrder(MEMBER)

val ORDER_PRODUCT = OrderProduct(
    product = PRODUCT,
    order = ORDER,
    count = COUNT,
    size = PRODUCT_SIZE,
    color = COLOR
)

val GET_ORDER_RESPONSE = GetOrderDto(
    orderIdx = ORDER_IDX,
    productName = PRODUCT_NAME,
    brandName = BRAND_NAME,
    productIdx = PRODUCT_IDX,
    orderCreatedAt = LocalDateTime.now(),
    quantity = QUANTITY,
    price = PRICE,
    size = PRODUCT_SIZE,
    color = COLOR,
    status = ORDER_STATUS
)

val GET_ORDER_RESPONSE_LIST = mutableListOf(GET_ORDER_RESPONSE, GET_ORDER_RESPONSE)

val orderRepository = mockk<OrderRepository>()
val orderProductRepository = mockk<OrderProductRepository>()
val orderProductCustomRepository = mockk<OrderProductCustomRepository>()
val productRepository = mockk<ProductRepository>()
val currentMemberUtil = mockk<CurrentMemberUtil>(relaxed = true)
val memberRepository = mockk<MemberRepository>()
val orderService = mockk<OrderService>()