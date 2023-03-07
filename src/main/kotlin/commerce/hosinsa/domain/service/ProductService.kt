package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.product.GetProductFilterDto
import commerce.hosinsa.domain.dto.product.ProductResponse
import commerce.hosinsa.domain.dto.product.RegistrationProductDto
import commerce.hosinsa.domain.dto.product.UpdateProductDto
import commerce.hosinsa.domain.repository.BrandRepository
import commerce.hosinsa.domain.repository.ProductCustomRepository
import commerce.hosinsa.domain.repository.ProductRepository
import commerce.hosinsa.global.extension.soldOut
import commerce.hosinsa.global.extension.toProduct
import commerce.hosinsa.global.extension.toProductResponse
import commerce.hosinsa.global.extension.updateProduct
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val productCustomRepository: ProductCustomRepository
) {
    fun registrationProduct(registrationProductDto: RegistrationProductDto) {

        brandRepository.findByName(registrationProductDto.brandName).also { brand ->
            if (brand == null) throw CustomException(ErrorCode.BRAND_NOT_FOUND)

            productRepository.save(registrationProductDto.toProduct(brand))
        }
    }

    @Transactional
    fun updateProduct(updateProductDto: UpdateProductDto) =
        productRepository.findByIdx(updateProductDto.productId).also { product ->
            if (product == null) throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)
        }!!.updateProduct(updateProductDto)

    @Transactional
    fun updateIsSoldOut(idx: Int) =
        productRepository.findByIdx(idx).also { product ->
            if (product == null) throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)
        }!!.soldOut()

    fun getProduct(idx: Int): ProductResponse =
        productRepository.findByIdx(idx).also { product ->
            if (product == null) throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)
        }!!.toProductResponse()

    fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable) =
        productCustomRepository.findByFilter(getProductFilterDto, pageable)
}