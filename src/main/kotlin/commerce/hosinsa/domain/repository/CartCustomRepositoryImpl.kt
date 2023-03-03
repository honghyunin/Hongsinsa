package commerce.hosinsa.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hosinsa.domain.dto.cart.CartResponse
import commerce.hosinsa.domain.dto.cart.QCartResponse
import commerce.hosinsa.entity.cart.QCart.cart
import commerce.hosinsa.entity.member.QMember.member
import commerce.hosinsa.entity.product.QProduct.product
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
            .where(member.idx.eq(memberIdx))
            .fetch()
}