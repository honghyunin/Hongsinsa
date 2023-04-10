package commerce.hongsinsa.repository.order

import commerce.hongsinsa.entity.order.Order
import commerce.hongsinsa.entity.order.OrderProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository: JpaRepository<OrderProduct, Int> {
    fun findAllByOrderAndIsDeleteFalse(order: Order): MutableList<OrderProduct>
}