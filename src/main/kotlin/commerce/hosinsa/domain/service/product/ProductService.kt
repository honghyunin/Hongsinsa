package commerce.hosinsa.domain.service.product

import commerce.hosinsa.domain.dto.product.GetProductFilterDto
import commerce.hosinsa.domain.dto.product.ProductResponse
import commerce.hosinsa.domain.dto.product.RegistrationProductDto
import commerce.hosinsa.domain.dto.product.UpdateProductDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductService {
    fun registrationProduct(registrationProductDto: RegistrationProductDto)
    fun updateProduct(updateProductDto: UpdateProductDto)
    fun updateIsSoldOut(idx: Int)
    fun getProduct(idx: Int): ProductResponse
    fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<ProductResponse>
}