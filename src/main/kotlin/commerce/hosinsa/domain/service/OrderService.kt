package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.order.GetOrderResponse
import commerce.hosinsa.domain.dto.order.OrderRequestDto
import commerce.hosinsa.domain.repository.OrderProductCustomRepository
import commerce.hosinsa.domain.repository.OrderProductRepository
import commerce.hosinsa.domain.repository.OrderRepository
import commerce.hosinsa.entity.member.Member
import commerce.hosinsa.entity.order.Order
import commerce.hosinsa.entity.order.OrderProduct
import commerce.hosinsa.entity.order.OrderStatus
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import commerce.hosinsa.global.extension.toOrder
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

    fun getOrder(memberIdx: Int): MutableList<GetOrderResponse> {
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