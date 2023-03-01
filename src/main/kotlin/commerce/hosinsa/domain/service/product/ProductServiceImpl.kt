package commerce.hosinsa.domain.service.product

import commerce.hosinsa.repository.brand.BrandRepository
import commerce.hosinsa.dto.product.GetProductFilterDto
import commerce.hosinsa.dto.product.ProductResponse
import commerce.hosinsa.dto.product.RegistrationProductDto
import commerce.hosinsa.dto.product.UpdateProductDto
import commerce.hosinsa.repository.product.ProductCustomRepository
import commerce.hosinsa.repository.product.ProductRepository
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
        productRepository.findByProductId(updateProductDto.productId).also { product ->
            if (product == null) throw CustomException(PRODUCT_NOT_FOUND)
        }!!.updateProduct(updateProductDto)

    @Transactional
    override fun updateIsSoldOut(productId: Int) =
        productRepository.findByProductId(productId).also { product ->
            if (product == null) throw CustomException(PRODUCT_NOT_FOUND)
        }!!.soldOut()

    override fun getProduct(productId: Int): ProductResponse =
        productRepository.findByProductId(productId).also { product ->
            if (product == null) throw CustomException(PRODUCT_NOT_FOUND)
        }!!.toProductResponse()

    override fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable) =
        productCustomRepository.findByFilter(getProductFilterDto, pageable)
}