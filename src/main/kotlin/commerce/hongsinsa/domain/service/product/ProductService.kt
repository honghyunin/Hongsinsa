package commerce.hongsinsa.domain.service.product

import commerce.hongsinsa.domain.dto.product.GetProductDto
import commerce.hongsinsa.domain.dto.product.GetProductFilterDto
import commerce.hongsinsa.domain.dto.product.ProductOptionDto
import commerce.hongsinsa.domain.dto.product.UpdateProductDto
import commerce.hongsinsa.domain.repository.product.ProductOptionRepository
import commerce.hongsinsa.domain.repository.product.ProductQueryRepository
import commerce.hongsinsa.domain.repository.product.ProductRepository
import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.entity.product.ProductOption
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode.PRODUCT_NOT_FOUND
import commerce.hongsinsa.global.extension.soldOut
import commerce.hongsinsa.global.extension.toProductResponse
import commerce.hongsinsa.global.extension.updateProduct
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productQueryRepository: ProductQueryRepository,
    private val productOptionRepository: ProductOptionRepository,
) {
    fun registrationProduct(product: Product) {
        productRepository.save(product)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun updateProduct(updateProductDto: UpdateProductDto): Unit =
        productRepository.findByIdxAndIsDeleteFalse(updateProductDto.productIdx)?.updateProduct(updateProductDto)
            ?: throw CustomException(PRODUCT_NOT_FOUND)

    @Transactional(rollbackFor = [Exception::class])
    fun updateIsSoldOut(productIdx: Int) =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)?.soldOut() ?: throw CustomException(PRODUCT_NOT_FOUND)

    @Transactional(readOnly = true)
    fun getProductResponse(productIdx: Int): GetProductDto =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)
            ?.toProductResponse(productQueryRepository.findProductOptionResponseByProductIdx(productIdx))
            ?: throw CustomException(PRODUCT_NOT_FOUND)

    @Transactional(readOnly = true)
    fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable) =
        productQueryRepository.findByFilter(getProductFilterDto, pageable).also { productList ->
            if (productList.isEmpty) throw CustomException(PRODUCT_NOT_FOUND)
        }

    @Transactional(rollbackFor = [Exception::class])
    fun deleteProduct(productIdx: Int) = productRepository.findByIdxAndIsDeleteFalse(productIdx)
        ?.let { product ->
            product.isDelete = true
        } ?: throw CustomException(PRODUCT_NOT_FOUND)


    @Transactional(rollbackFor = [Exception::class])
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

    @Transactional(rollbackFor = [Exception::class])
    fun updateProductOption(productIdx: Int, newOptions: MutableList<ProductOptionDto>) {
        productOptionRepository.deleteAllInBatch(productOptionRepository.findByProductIdx(productIdx))
        addProductOption(productIdx, newOptions)
    }

    @Transactional(readOnly = true)
    fun getProduct(productIdx: Int) = productRepository.findByIdxAndIsDeleteFalse(productIdx)
        ?: throw CustomException(PRODUCT_NOT_FOUND)
}