package commerce.hongsinsa.domain.service.order

import commerce.hongsinsa.domain.dto.order.GetOrderDto
import commerce.hongsinsa.domain.dto.order.OrderRequestDto
import commerce.hongsinsa.domain.repository.order.OrderProductQueryRepository
import commerce.hongsinsa.domain.repository.order.OrderProductRepository
import commerce.hongsinsa.domain.repository.order.OrderQueryRepository
import commerce.hongsinsa.domain.repository.order.OrderRepository
import commerce.hongsinsa.domain.repository.product.ProductRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.order.Order
import commerce.hongsinsa.entity.order.OrderProduct
import commerce.hongsinsa.entity.order.OrderStatus
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode.*
import commerce.hongsinsa.global.extension.toOrder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val orderProductQueryRepository: OrderProductQueryRepository,
    private val orderQueryRepository: OrderQueryRepository,
    private val productRepository: ProductRepository
) {

    fun saveOrder(orderRequestDto: OrderRequestDto, member: Member) =
        orderRepository.save(orderRequestDto.toOrder(member))

    fun processOrderRequest(order: Order, orderRequestDto: OrderRequestDto) {
        orderRequestDto.productIdxList.forEach { productIdx ->
            val product = getProductIdx(productIdx)

            val quantity: Byte = orderRequestDto.productQuantities[productIdx]
                ?: throw CustomException(PRODUCT_QUANTITY_NOT_FOUND)

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
    }

    fun decreaseStock(productQuantities: MutableMap<Int, Byte>) {
        productQuantities.forEach { (productIdx, quantity) ->
            val product = getProductIdx(productIdx)
            product.stock -= quantity
        }
    }

    @Transactional(readOnly = true)
    fun getOrder(memberIdx: Int): MutableList<GetOrderDto> {
        val orders = orderProductQueryRepository.findGetOrderResponsesByMemberIdx(memberIdx)

        if (orders.isEmpty())
            throw CustomException(ORDER_NOT_FOUND)

        return orders
    }

    @Transactional(rollbackFor = [Exception::class])
    fun cancelOrder(orderIdx: Int) {
        val order = orderQueryRepository.findByIdxAndStatusOrderReceived(orderIdx)?.also { order ->
            order.status = OrderStatus.ORDER_CANCEL
        } ?: throw CustomException(ORDER_NOT_FOUND)

        orderProductRepository.findAllByOrderAndIsDeleteFalse(order).forEach { orderProduct ->
            orderProduct.isDelete = true
        }
    }

    @Transactional(readOnly = true)
    fun getProductIdx(productIdx: Int) =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)
            ?: throw CustomException(PRODUCT_NOT_FOUND)
}