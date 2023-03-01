package commerce.hosinsa.domain.product.fixtures

import commerce.hosinsa.dto.product.GetProductFilterDto
import commerce.hosinsa.dto.product.ProductResponse
import commerce.hosinsa.dto.product.RegistrationProductDto
import commerce.hosinsa.dto.product.UpdateProductDto
import commerce.hosinsa.entity.product.Price
import commerce.hosinsa.service.product.ProductService
import io.mockk.mockk
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

const val PRODUCT_ID = 1
const val PRODUCT_NAME = "OVAINE-GYERUIK"
const val PRICE = 20000
const val CATEGORY = "category"
const val GENDER: Char = 'M'
const val STOCK = 10000000
const val BRAND_NAME = "AVANT-GARDE"

val registrationProductDto = RegistrationProductDto(
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    stock = STOCK,
    brandName = BRAND_NAME
)

val updateProductDto = UpdateProductDto(
    productId = PRODUCT_ID,
    name = PRODUCT_NAME,
    price = PRICE,
    category = CATEGORY,
    gender = GENDER,
    stock = STOCK
)

val productResponse = ProductResponse(
    productId = PRODUCT_ID,
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
val pageable = Pageable.ofSize(2)
val products = mutableListOf(productResponse, productResponse)
val productsPage = PageImpl(products, pageable, 2)

val productService = mockk<ProductService>()