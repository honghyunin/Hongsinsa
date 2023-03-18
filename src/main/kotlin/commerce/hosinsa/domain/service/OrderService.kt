package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.order.GetOrderResponse
import commerce.hosinsa.domain.dto.order.OrderRequestDto
import commerce.hosinsa.domain.repository.*
import commerce.hosinsa.entity.order.OrderProduct
import commerce.hosinsa.entity.order.OrderStatus
import commerce.hosinsa.global.config.utils.CurrentUserUtil
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import commerce.hosinsa.global.extension.toOrder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val orderProductCustomRepository: OrderProductCustomRepository,
    private val productRepository: ProductRepository,
    private val currentUserUtil: CurrentUserUtil,
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun orderRequest(orderRequestDto: OrderRequestDto) {
        val order = orderRepository.save(orderRequestDto.toOrder(currentUserUtil.currentUser!!))

        val productQuantities = mutableMapOf<Int, Byte>()

        orderRequestDto.productIdxList.forEach { productIdx ->
            val product = productRepository.findByIdxAndIsDeleteFalse(productIdx) ?: throw CustomException(PRODUCT_NOT_FOUND)

            val quantity: Byte = orderRequestDto.productQuantities[productIdx]
                ?: throw CustomException(PRODUCT_QUANTITY_NOT_FOUND)

            productQuantities[product.idx!!] = (productQuantities.getOrDefault(product.idx, 0) + quantity).toByte()

            orderProductRepository.save(
                OrderProduct(
                    product = product,
                    order = order,
                    count = quantity,
                    size = orderRequestDto.size,
                    color = orderRequestDto.color
                )
            )
        }

        productQuantities.forEach { (productId, quantity) ->
            val product = productRepository.findById(productId).orElseThrow()
            product.stock -= quantity
        }
    }

    fun getOrder(memberIdx: Int): MutableList<GetOrderResponse> {

        if (!memberRepository.existsById(memberIdx))
            throw CustomException(MEMBER_NOT_FOUND)

        val orders = orderProductCustomRepository.findGetOrderResponsesByMemberIdx(memberIdx)

        if (orders.isEmpty())
            throw CustomException(ORDER_NOT_FOUND)

        return orders
    }

    fun cancelOrder(orderIdx: Int) {
        currentUserUtil.currentUser ?: throw CustomException(FORBIDDEN)

        val order = orderRepository.findById(orderIdx).orElseThrow { throw CustomException(ORDER_NOT_FOUND) }

        orderProductRepository.findAllByOrderAndIsDeleteFalse(order).forEach { orderProduct ->
            orderProduct.isDelete = true
        }

        order.status = OrderStatus.ORDER_CANCEL
    }
}