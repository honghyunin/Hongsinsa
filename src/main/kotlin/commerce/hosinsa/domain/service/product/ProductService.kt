package commerce.hosinsa.domain.service.product

import commerce.hosinsa.dto.product.GetProductFilterDto
import commerce.hosinsa.dto.product.ProductResponse
import commerce.hosinsa.dto.product.RegistrationProductDto
import commerce.hosinsa.dto.product.UpdateProductDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductService {
    fun registrationProduct(registrationProductDto: RegistrationProductDto)
    fun updateProduct(updateProductDto: UpdateProductDto)
    fun updateIsSoldOut(productId: Int)
    fun getProduct(productId: Int): ProductResponse
    fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable): Page<ProductResponse>
}