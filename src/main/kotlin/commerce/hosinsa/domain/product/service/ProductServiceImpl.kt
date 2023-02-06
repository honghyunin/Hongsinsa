package commerce.hosinsa.domain.product.service

import commerce.hosinsa.domain.brand.repository.BrandRepository
import commerce.hosinsa.domain.product.dto.RegistrationProductDto
import commerce.hosinsa.domain.product.dto.UpdateProductDto
import commerce.hosinsa.domain.product.repository.ProductRepository
import commerce.hosinsa.global.config.utils.soldOut
import commerce.hosinsa.global.config.utils.toProduct
import commerce.hosinsa.global.config.utils.updateProduct
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
) : ProductService {
    override fun registrationProduct(registrationProductDto: RegistrationProductDto) {

        if (existsByName(registrationProductDto.name))
            throw CustomException(PRODUCT_IS_ALREADY_EXISTS)

        val brand = brandRepository.findByName(registrationProductDto.brandName)
            ?: throw CustomException(BRAND_NOT_FOUND)

        productRepository.save(registrationProductDto.toProduct(brand))
    }

    @Transactional
    override fun updateProduct(updateProductDto: UpdateProductDto) {

        val product = productRepository.findByProductId(updateProductDto.productId)
            ?: throw CustomException(PRODUCT_NOT_FOUND)

        product.updateProduct(updateProductDto)
    }

    @Transactional
    override fun updateIsSoldOut(productName: String) {
        val product = productRepository.findByName(productName)
            ?: throw CustomException(PRODUCT_NOT_FOUND)

        product.soldOut()
    }


    private fun existsByName(name: String) = productRepository.existsByName(name)
}