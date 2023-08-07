package commerce.hongsinsa.repository.request

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.dto.request.GetRequestDto
import commerce.hongsinsa.dto.request.QGetRequestDto
import commerce.hongsinsa.entity.brand.QBrand.brand
import commerce.hongsinsa.entity.member.QMember.member
import commerce.hongsinsa.entity.product.QProduct.product
import commerce.hongsinsa.entity.request.QRequest.request
import commerce.hongsinsa.entity.request.QRequestProduct.requestProduct
import org.springframework.stereotype.Service

@Service
class RequestProductQueryRepository(private val queryFactory: JPAQueryFactory) {
    fun findGetRequestResponsesByMemberIdx(memberIdx: Int): MutableList<GetRequestDto> = queryFactory.select(
        QGetRequestDto(
            request.idx,
            product.name,
            product.brand.name,
            product.idx,
            request.createdAt,
            requestProduct.count,
            product.price,
            requestProduct.size,
            requestProduct.color,
            request.status
        )
    )
        .from(requestProduct)
        .innerJoin(requestProduct.product, product)
        .innerJoin(product.brand, brand)
        .innerJoin(requestProduct.request, request)
        .innerJoin(request.member, member)
        .where(request.member.idx.eq(memberIdx))
        .fetch()
}