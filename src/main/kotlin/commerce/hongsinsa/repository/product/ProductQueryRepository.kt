package commerce.hongsinsa.repository.product

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.dto.product.*
import commerce.hongsinsa.entity.brand.QBrand.brand
import commerce.hongsinsa.entity.product.Price
import commerce.hongsinsa.entity.product.Price.*
import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.entity.product.QProduct.product
import commerce.hongsinsa.entity.product.QProductOption.productOption
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ProductQueryRepository(private val query: JPAQueryFactory) {

    val FALSE = false

    fun findByFilter(getProductFilterDto: GetProductFilterDto, pageable: Pageable): PageImpl<Product> {
        val products = query.selectFrom(product)
            .leftJoin(product.brand, brand)
            .where(
                product.isDelete.eq(FALSE)
                    .and(createWhereFilter(getProductFilterDto))
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count: Long? = query.select(product.count())
            .from(product)
            .fetchOne()

        return PageImpl(products, pageable, count!!)
    }

    private fun getProductOptions() =
        JPAExpressions.selectFrom(productOption)
            .where(productOption.product.idx.eq(product.idx))

    fun findByIdxList(productIdxList: MutableList<Int>): MutableList<Product> {
        return query.selectFrom(product)
            .where(product.idx.`in`(productIdxList))
            .fetch()
    }

    fun findProductOptionResponseByProductIdx(productIdx: Int): MutableList<ProductOptionResponse> =
        query.select(
            QProductOptionResponse(
                productOption.idx,
                productOption.color,
                productOption.size
            )
        )
            .from(productOption)
            .innerJoin(productOption.product, product)
            .where(product.idx.eq(productIdx))
            .fetch()

    private fun createWhereFilter(getProductFilterDto: GetProductFilterDto): BooleanBuilder =
        getProductFilterDto.let { filterDto ->
            BooleanBuilder().apply {
                if (filterDto.name != null) this.and(product.name.eq(filterDto.name))
                if (filterDto.price != null) getPriceCondition(filterDto.price!!, this)
                if (filterDto.category != null) this.and(product.category.eq(filterDto.category))
                if (filterDto.gender != null) this.and(product.gender.eq(filterDto.gender))
                if (filterDto.brandName != null) this.and(product.brand.name.eq(filterDto.brandName))
                this.and(product.isSoldOut.eq(false))
            }
        }

    private fun getPriceCondition(price: Price, builder: BooleanBuilder): BooleanBuilder? = when (price) {
        PRICE1 -> builder.and(product.price.between(PRICE1.minPrice, PRICE1.maxPrice))
        PRICE2 -> builder.and(product.price.between(PRICE2.minPrice, PRICE2.maxPrice))
        PRICE3 -> builder.and(product.price.between(PRICE3.minPrice, PRICE3.maxPrice))
        else -> {
            null
        }
    }
}