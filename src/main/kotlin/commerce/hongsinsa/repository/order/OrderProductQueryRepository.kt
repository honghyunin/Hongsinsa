package commerce.hongsinsa.repository.order

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.dto.order.GetOrderDto
import commerce.hongsinsa.dto.order.QGetOrderDto
import commerce.hongsinsa.entity.brand.QBrand.brand
import commerce.hongsinsa.entity.member.QMember.member
import commerce.hongsinsa.entity.order.QOrder.order
import commerce.hongsinsa.entity.order.QOrderProduct.orderProduct
import commerce.hongsinsa.entity.product.QProduct.product
import org.springframework.stereotype.Service

@Service
class OrderProductQueryRepository(private val queryFactory: JPAQueryFactory) {
    fun findGetOrderResponsesByMemberIdx(memberIdx: Int): MutableList<GetOrderDto> = queryFactory.select(
        QGetOrderDto(
            order.idx,
            product.name,
            product.brand.name,
            product.idx,
            order.createdAt,
            orderProduct.count,
            product.price,
            orderProduct.size,
            orderProduct.color,
            order.status
        )
    )
        .from(orderProduct)
        .innerJoin(orderProduct.product, product)
        .innerJoin(product.brand, brand)
        .innerJoin(orderProduct.order, order)
        .innerJoin(order.member, member)
        .where(order.member.idx.eq(memberIdx))
        .fetch()
}