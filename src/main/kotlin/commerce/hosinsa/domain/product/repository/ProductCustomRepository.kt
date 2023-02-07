package commerce.hosinsa.domain.product.repository

import commerce.hosinsa.domain.product.dto.GetProductFilterDto
import commerce.hosinsa.domain.product.dto.ProductResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductCustomRepository {
    fun findByFilter(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<ProductResponse>
}