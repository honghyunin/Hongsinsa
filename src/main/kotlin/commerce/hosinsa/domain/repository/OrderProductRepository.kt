package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.order.Order
import commerce.hosinsa.entity.order.OrderProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository: JpaRepository<OrderProduct, Int> {
    fun findAllByOrder(order: Order): MutableList<OrderProduct>
}