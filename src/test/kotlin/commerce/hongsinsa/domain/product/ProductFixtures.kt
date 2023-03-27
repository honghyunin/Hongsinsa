package commerce.hongsinsa.domain.product

import commerce.hongsinsa.domain.brand.BRAND
import commerce.hongsinsa.domain.dto.product.*
import commerce.hongsinsa.domain.repository.brand.BrandRepository
import commerce.hongsinsa.domain.repository.product.ProductRepository
import commerce.hongsinsa.domain.service.ProductService
import commerce.hongsinsa.entity.product.Price
import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.entity.product.ProductSize
import io.mockk.mockk
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

const val PRODUCT_IDX = 1
const val PRODUCT_NAME = "OVAINE-GYERUIK"
const val PRICE = 20000
const val CATEGORY = "category"
const val GENDER: Char = 'M'
const val STOCK = 10000000
const val BRAND_NAME = "AVANT-GARDE"
const val COLOR = "검정"
val COLOR_LIST = mutableSetOf("검정", "흰색", "파랑")
val PRODUCT_SIZE_LIST = mutableSetOf(ProductSize.L, ProductSize.S, ProductSize.XS, ProductSize.M)
val PRODUCT_SIZE = ProductSize.L
val PRODUCT = Product(
    PRODUCT_NAME,
    PRICE,
    CATEGORY,
    GENDER,
    STOCK,
    BRAND
)
val OPTIONS = mutableListOf(ProductOptionResponse(productIdx = PRODUCT_IDX, color = COLOR, size = PRODUCT_SIZE))

val REGISTRATION_PRODUCT_DTO = RegistrationProductDto(
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    stock = STOCK,
    brandName = BRAND_NAME,
)

val UPDATE_PRODUCT_DTO = UpdateProductDto(
    productIdx = PRODUCT_IDX,
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    stock = STOCK
)

val PRODUCT_RESPONSE = ProductResponse(
    productId = PRODUCT_IDX,
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    brand = BRAND_NAME,
    options = OPTIONS
)

val GET_PRODUCT_FILTER_DTO = GetProductFilterDto(
    name = PRODUCT_NAME,
    price = Price.PRICE1,
    category = CATEGORY,
    gender = GENDER,
    brandName = BRAND_NAME
)


val pageable = Pageable.ofSize(2)
val products = mutableListOf(PRODUCT_RESPONSE, PRODUCT_RESPONSE)
val productsPage = PageImpl(products, pageable, 2)

val brandRepository = mockk<BrandRepository>()
val productRepository = mockk<ProductRepository>()
val productService = mockk<ProductService>()