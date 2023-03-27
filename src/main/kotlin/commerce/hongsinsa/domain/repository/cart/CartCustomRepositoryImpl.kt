package commerce.hongsinsa.domain.repository.cart

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.domain.dto.cart.CartResponse
import commerce.hongsinsa.domain.dto.cart.QCartResponse
import commerce.hongsinsa.entity.cart.QCart.cart
import commerce.hongsinsa.entity.member.QMember.member
import commerce.hongsinsa.entity.product.QProduct.product
import org.springframework.stereotype.Repository

@Repository
class CartCustomRepositoryImpl(private val queryFactory: JPAQueryFactory) : CartCustomRepository {
    override fun findProductsByMemberIdx(memberIdx: Int): MutableList<CartResponse> =
        queryFactory.select(
            QCartResponse(
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