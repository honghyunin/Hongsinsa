package commerce.hosinsa.domain.repository.product

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hosinsa.domain.dto.product.GetProductFilterDto
import commerce.hosinsa.domain.dto.product.ProductResponse
import commerce.hosinsa.domain.dto.product.QProductResponse
import commerce.hosinsa.entity.brand.QBrand.brand
import commerce.hosinsa.entity.product.Price
import commerce.hosinsa.entity.product.Price.*
import commerce.hosinsa.entity.product.QProduct.product
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ProductCustomRepositoryImpl(private val query: JPAQueryFactory) : ProductCustomRepository {
    override fun findByFilter(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<ProductResponse> {
        val products = query.select(
            QProductResponse(
                product.idx,
                product.name,
                product.price,
                product.category,
                product.gender,
                product.brand.name
            )
        ).from(product)
            .innerJoin(product.brand, brand)
            .where(createWhereFilter(getProductFilterDto))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count: Long? = query.select(product.count())
            .from(product)
            .fetchOne()

        return PageImpl(products, pageable, count!!)
    }

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