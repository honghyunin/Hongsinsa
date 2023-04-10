package commerce.hongsinsa.service.order

import commerce.hongsinsa.dto.order.GetOrderDto
import commerce.hongsinsa.dto.order.OrderRequestDto
import commerce.hongsinsa.repository.order.OrderProductQueryRepository
import commerce.hongsinsa.repository.order.OrderProductRepository
import commerce.hongsinsa.repository.order.OrderQueryRepository
import commerce.hongsinsa.repository.order.OrderRepository
import commerce.hongsinsa.repository.product.ProductRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.order.Order
import commerce.hongsinsa.entity.order.OrderProduct
import commerce.hongsinsa.entity.order.OrderStatus
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.*
import commerce.hongsinsa.extension.toOrder
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