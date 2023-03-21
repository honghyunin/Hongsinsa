package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.order.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Int> {
    fun findByIdx(orderIdx: Int): Order?
    fun findAllByMember(memberIdx: Int): MutableList<Order>
}
