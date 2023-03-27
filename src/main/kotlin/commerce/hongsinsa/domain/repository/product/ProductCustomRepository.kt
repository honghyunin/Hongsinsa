package commerce.hongsinsa.domain.repository.product

import commerce.hongsinsa.domain.dto.product.GetProductFilterDto
import commerce.hongsinsa.domain.dto.product.ProductOptionResponse
import commerce.hongsinsa.domain.dto.product.ProductResponse
import commerce.hongsinsa.entity.product.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductCustomRepository {
    fun findByFilter(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<ProductResponse>
    fun findByIdxList(productIdxList: MutableList<Int>): MutableList<Product>
    fun findProductOptionResponseByProductIdx(productIdx: Int): MutableList<ProductOptionResponse>
}