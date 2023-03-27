package commerce.hongsinsa.domain.repository.order

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.entity.order.Order
import commerce.hongsinsa.entity.order.OrderStatus
import commerce.hongsinsa.entity.order.QOrder.order
import org.springframework.stereotype.Repository

@Repository
class OrderQueryRepository(private val queryFactory: JPAQueryFactory) {

    fun findByIdxAndStatusOrderReceived(orderIdx: Int): Order? = queryFactory.selectFrom(order)
        .where(order.idx.eq(orderIdx)
            .and(order.status.eq(OrderStatus.ORDER_RECEIVED)))
        .fetchOne()
}