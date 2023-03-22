package commerce.hongsinsa.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.domain.dto.order.GetOrderResponse
import commerce.hongsinsa.domain.dto.order.QGetOrderResponse
import commerce.hongsinsa.entity.brand.QBrand.brand
import commerce.hongsinsa.entity.member.QMember.member
import commerce.hongsinsa.entity.order.QOrder.order
import commerce.hongsinsa.entity.order.QOrderProduct.orderProduct
import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.entity.product.QProduct.product
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

    override fun findProductByProductIdxAndIsDeleteFalse(productIdx: Int): Product? = queryFactory
        .selectFrom(product)
        .where(product.idx.eq(productIdx)
            .and(product.isDelete.eq(false)))
        .fetchOne()
}