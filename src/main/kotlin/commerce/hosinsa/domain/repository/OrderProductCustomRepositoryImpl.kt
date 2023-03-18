package commerce.hosinsa.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hosinsa.domain.dto.order.GetOrderResponse
import commerce.hosinsa.domain.dto.order.QGetOrderResponse
import commerce.hosinsa.entity.brand.QBrand.brand
import commerce.hosinsa.entity.member.QMember.member
import commerce.hosinsa.entity.order.QOrder.order
import commerce.hosinsa.entity.order.QOrderProduct.orderProduct
import commerce.hosinsa.entity.product.QProduct.product
import org.springframework.stereotype.Service

@Service
class OrderProductCustomRepositoryImpl(private val queryFactory: JPAQueryFactory) : OrderProductCustomRepository {
    override fun findGetOrderResponsesByMemberIdx(memberIdx: Int): MutableList<GetOrderResponse> = queryFactory.select(
        QGetOrderResponse(
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