package commerce.hosinsa.domain.repository.product

import commerce.hosinsa.dto.product.GetProductFilterDto
import commerce.hosinsa.dto.product.ProductResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductCustomRepository {
    fun findByFilter(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<ProductResponse>
}