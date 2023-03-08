package commerce.hosinsa.domain.product

import commerce.hosinsa.domain.brand.BRAND
import commerce.hosinsa.domain.dto.product.GetProductFilterDto
import commerce.hosinsa.domain.dto.product.ProductResponse
import commerce.hosinsa.domain.dto.product.RegistrationProductDto
import commerce.hosinsa.domain.dto.product.UpdateProductDto
import commerce.hosinsa.domain.service.ProductService
import commerce.hosinsa.entity.product.Price
import commerce.hosinsa.entity.product.Product
import commerce.hosinsa.entity.product.ProductSize
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

val registrationProductDto = RegistrationProductDto(
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    stock = STOCK,
    brandName = BRAND_NAME,
    color = COLOR_LIST,
    size = PRODUCT_SIZE_LIST
)

val updateProductDto = UpdateProductDto(
    productId = PRODUCT_IDX,
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    stock = STOCK,
    color = COLOR_LIST,
    size = PRODUCT_SIZE_LIST
)

val productResponse = ProductResponse(
    productId = PRODUCT_IDX,
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    brand = BRAND_NAME
)

val getProductFilterDto = GetProductFilterDto(
    name = PRODUCT_NAME,
    price = Price.PRICE1,
    category = CATEGORY,
    gender = GENDER,
    brandName = BRAND_NAME
)

val PRODUCT = Product(
    PRODUCT_NAME,
    PRICE,
    CATEGORY,
    GENDER,
    PRODUCT_SIZE_LIST,
    COLOR_LIST,
    STOCK,
    BRAND
)

val pageable = Pageable.ofSize(2)
val products = mutableListOf(productResponse, productResponse)
val productsPage = PageImpl(products, pageable, 2)

val productService = mockk<ProductService>()