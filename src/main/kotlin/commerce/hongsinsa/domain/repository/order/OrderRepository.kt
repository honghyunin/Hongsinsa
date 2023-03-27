package commerce.hongsinsa.domain.repository.order

import commerce.hongsinsa.entity.order.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Int>