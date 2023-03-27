package commerce.hongsinsa.domain.repository.product

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.domain.dto.product.*
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
class ProductCustomRepositoryImpl(private val query: JPAQueryFactory) : ProductCustomRepository {
    override fun findByFilter(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<GetProductDto> {
        val products = query.select(
            QProductInfoDto(
                product.idx,
                product.name,
                product.price,
                product.category,
                product.gender,
                product.brand.name
            )
        ).from(product)
            .innerJoin(product.brand, brand)
            .where(product.isDelete.eq(false)
                .and(createWhereFilter(getProductFilterDto)))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count: Long? = query.select(product.count())
            .from(product)
            .fetchOne()

        val productOptions = query.select(
            QProductOptionResponse(
                productOption.idx,
                productOption.color,
                productOption.size
            )
        ).from(productOption)
            .where(productOption.product.idx.`in`(products.map { it.productIdx }))
            .fetch().toMutableList()

        val getProductDtoLists: MutableList<GetProductDto> = products.map { product ->
            GetProductDto(
                product.productIdx,
                product.name,
                product.price,
                product.category,
                product.gender,
                product.brand,
                productOptions.filter { it.productIdx == product.productIdx }.toMutableList()
            )
        }.toMutableList()

        return PageImpl(getProductDtoLists, pageable, count!!)
    }

    override fun findByIdxList(productIdxList: MutableList<Int>): MutableList<Product> {
        return query.selectFrom(product)
            .where(product.idx.`in`(productIdxList))
            .fetch()
    }

    override fun findProductOptionResponseByProductIdx(productIdx: Int): MutableList<ProductOptionResponse> =
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