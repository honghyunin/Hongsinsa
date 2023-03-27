package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.dto.product.GetProductFilterDto
import commerce.hongsinsa.domain.dto.product.ProductOptionDto
import commerce.hongsinsa.domain.dto.product.ProductResponse
import commerce.hongsinsa.domain.dto.product.UpdateProductDto
import commerce.hongsinsa.domain.repository.product.ProductCustomRepository
import commerce.hongsinsa.domain.repository.product.ProductOptionRepository
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
import javax.transaction.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productCustomRepository: ProductCustomRepository,
    private val productOptionRepository: ProductOptionRepository,
) {
    fun registrationProduct(product: Product) {
        productRepository.save(product)
    }

    @Transactional
    fun updateProduct(updateProductDto: UpdateProductDto): Unit =
        productRepository.findByIdxAndIsDeleteFalse(updateProductDto.productIdx)?.updateProduct(updateProductDto)
            ?: throw CustomException(PRODUCT_NOT_FOUND)

    @Transactional
    fun updateIsSoldOut(productIdx: Int) =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)?.soldOut() ?: throw CustomException(PRODUCT_NOT_FOUND)

    fun getProductResponse(productIdx: Int): ProductResponse =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)
            ?.toProductResponse(productCustomRepository.findProductOptionResponseByProductIdx(productIdx))
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