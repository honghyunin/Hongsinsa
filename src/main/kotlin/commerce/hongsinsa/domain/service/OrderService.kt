package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.dto.order.GetOrderDto
import commerce.hongsinsa.domain.dto.order.OrderRequestDto
import commerce.hongsinsa.domain.repository.order.OrderProductCustomRepository
import commerce.hongsinsa.domain.repository.order.OrderProductRepository
import commerce.hongsinsa.domain.repository.order.OrderRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.order.Order
import commerce.hongsinsa.entity.order.OrderProduct
import commerce.hongsinsa.entity.order.OrderStatus
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode.*
import commerce.hongsinsa.global.extension.toOrder
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val orderProductCustomRepository: OrderProductCustomRepository,
) {

    fun saveOrder(orderRequestDto: OrderRequestDto, member: Member) =
        orderRepository.save(orderRequestDto.toOrder(member))

    fun processOrderRequest(order: Order, orderRequestDto: OrderRequestDto) {
        val productQuantities = mutableMapOf<Int, Byte>()

        orderRequestDto.productIdxList.forEach { productIdx ->
            val product = getOrderProductByProductIdx(productIdx)

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

            decreaseStock(productQuantities)
        }
    }

    fun decreaseStock(productQuantities: MutableMap<Int, Byte>) {
        productQuantities.forEach { (productIdx, quantity) ->
            val product = getOrderProductByProductIdx(productIdx)
            product.stock -= quantity
        }
    }

    fun getOrder(memberIdx: Int): MutableList<GetOrderDto> {
        val orders = orderProductCustomRepository.findGetOrderResponsesByMemberIdx(memberIdx)

        if (orders.isEmpty())
            throw CustomException(ORDER_NOT_FOUND)

        return orders
    }

    fun cancelOrder(orderIdx: Int) {
        val order = orderRepository.findByIdx(orderIdx) ?: throw CustomException(ORDER_NOT_FOUND)

        orderProductRepository.findAllByOrderAndIsDeleteFalse(order).forEach { orderProduct ->
            orderProduct.isDelete = true
        }

        order.status = OrderStatus.ORDER_CANCEL
    }

    fun getOrderProductByProductIdx(productIdx: Int) =
        orderProductCustomRepository.findProductByProductIdxAndIsDeleteFalse(productIdx)
            ?: throw CustomException(PRODUCT_NOT_FOUND)
}