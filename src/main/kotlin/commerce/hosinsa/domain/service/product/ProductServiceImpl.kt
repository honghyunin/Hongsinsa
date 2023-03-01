package commerce.hosinsa.domain.service.product

import commerce.hosinsa.domain.dto.product.GetProductFilterDto
import commerce.hosinsa.domain.dto.product.ProductResponse
import commerce.hosinsa.domain.dto.product.RegistrationProductDto
import commerce.hosinsa.domain.dto.product.UpdateProductDto
import commerce.hosinsa.domain.repository.brand.BrandRepository
import commerce.hosinsa.domain.repository.product.ProductCustomRepository
import commerce.hosinsa.domain.repository.product.ProductRepository
import commerce.hosinsa.global.config.utils.soldOut
import commerce.hosinsa.global.config.utils.toProduct
import commerce.hosinsa.global.config.utils.toProductResponse
import commerce.hosinsa.global.config.utils.updateProduct
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val productCustomRepository: ProductCustomRepository
) : ProductService {
    override fun registrationProduct(registrationProductDto: RegistrationProductDto) {

        brandRepository.findByName(registrationProductDto.brandName).also { brand ->
            if (brand == null) throw CustomException(BRAND_NOT_FOUND)

            productRepository.save(registrationProductDto.toProduct(brand))
        }
    }

    @Transactional
    override fun updateProduct(updateProductDto: UpdateProductDto) =
        productRepository.findByIdx(updateProductDto.productId).also { product ->
            if (product == null) throw CustomException(PRODUCT_NOT_FOUND)
        }!!.updateProduct(updateProductDto)

    @Transactional
    override fun updateIsSoldOut(idx: Int) =
        productRepository.findByIdx(idx).also { product ->
            if (product == null) throw CustomException(PRODUCT_NOT_FOUND)
        }!!.soldOut()

    override fun getProduct(idx: Int): ProductResponse =
        productRepository.findByIdx(idx).also { product ->
            if (product == null) throw CustomException(PRODUCT_NOT_FOUND)
        }!!.toProductResponse()

    override fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable) =
        productCustomRepository.findByFilter(getProductFilterDto, pageable)
}