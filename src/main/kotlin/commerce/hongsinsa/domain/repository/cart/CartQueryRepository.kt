package commerce.hongsinsa.domain.repository.cart

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.domain.dto.cart.GetCartDto
import commerce.hongsinsa.domain.dto.cart.QGetCartDto
import commerce.hongsinsa.entity.cart.QCart.cart
import commerce.hongsinsa.entity.member.QMember.member
import commerce.hongsinsa.entity.product.QProduct.product
import org.springframework.stereotype.Repository

@Repository
class CartQueryRepository(private val queryFactory: JPAQueryFactory) {
    fun findProductsByMemberIdx(memberIdx: Int): MutableList<GetCartDto> =
        queryFactory.select(
            QGetCartDto(
                product.name,
                product.price,
                product.stock,
                product.price
            )
        )
            .from(cart)
            .innerJoin(cart.product, product)
            .innerJoin(cart.member, member)
            .where(member.idx.eq(memberIdx)
                .and(cart.isDelete.eq(false)))
            .fetch()
}