package commerce.hosinsa.domain.repository

import commerce.hosinsa.domain.dto.product.GetProductFilterDto
import commerce.hosinsa.domain.dto.product.ProductResponse
import commerce.hosinsa.entity.product.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductCustomRepository {
    fun findByFilter(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<ProductResponse>
    fun findByIdxList(productIdxList: MutableList<Int>): MutableList<Product>
}