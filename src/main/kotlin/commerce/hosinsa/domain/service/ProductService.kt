package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.product.*
import commerce.hosinsa.domain.repository.BrandRepository
import commerce.hosinsa.domain.repository.ProductCustomRepository
import commerce.hosinsa.domain.repository.ProductOptionRepository
import commerce.hosinsa.domain.repository.ProductRepository
import commerce.hosinsa.entity.product.ProductOption
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import commerce.hosinsa.global.exception.ErrorCode.PRODUCT_NOT_FOUND
import commerce.hosinsa.global.extension.soldOut
import commerce.hosinsa.global.extension.toProduct
import commerce.hosinsa.global.extension.toProductResponse
import commerce.hosinsa.global.extension.updateProduct
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val productCustomRepository: ProductCustomRepository,
    private val productOptionRepository: ProductOptionRepository,
) {
    fun registrationProduct(registrationProductDto: RegistrationProductDto) {
        brandRepository.findByNameAndIsDeleteFalse(registrationProductDto.brandName)?.let { brand ->
            productRepository.save(registrationProductDto.toProduct(brand))
        } ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)
    }

    @Transactional
    fun updateProduct(updateProductDto: UpdateProductDto) =
        productRepository.findByIdxAndIsDeleteFalse(updateProductDto.productId)?.let { product ->
            product.updateProduct(updateProductDto)
        } ?: throw CustomException(PRODUCT_NOT_FOUND)

    @Transactional
    fun updateIsSoldOut(productIdx: Int) =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)?.soldOut() ?: throw CustomException(PRODUCT_NOT_FOUND)

    fun getProductResponse(productIdx: Int): ProductResponse =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)
            ?.toProductResponse(productOptionRepository.findByProductIdx(productIdx))
            ?: throw CustomException(PRODUCT_NOT_FOUND)

    fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable) =
        productCustomRepository.findByFilter(getProductFilterDto, pageable).also { productList ->
            if (productList.isEmpty) throw CustomException(PRODUCT_NOT_FOUND)
        }

    @Transactional
    fun deleteProduct(productIdx: Int) = productRepository.findByIdxAndIsDeleteFalse(productIdx)
        ?.let { product ->
            product.isDelete = true
        } ?: throw CustomException(PRODUCT_NOT_FOUND)

    fun addProductOption(productIdx: Int, newOptions: MutableList<ProductOptionDto>) {
        val product = getProduct(productIdx)
        val options = newOptions.map { dto ->
            ProductOption(
                product = product,
                color = dto.color,
                size = dto.size
            )
        }
        productOptionRepository.saveAll(options)
    }

    fun updateProductOption(productIdx: Int, newOptions: MutableList<ProductOptionDto>) {
        productOptionRepository.deleteAllInBatch(productOptionRepository.findByProductIdx(productIdx))
        addProductOption(productIdx, newOptions)
    }

    fun getProduct(productIdx: Int) = productRepository.findByIdxAndIsDeleteFalse(productIdx)
        ?: throw CustomException(PRODUCT_NOT_FOUND)
}