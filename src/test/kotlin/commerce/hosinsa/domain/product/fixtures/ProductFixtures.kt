package commerce.hosinsa.domain.product.fixtures

import commerce.hosinsa.domain.product.dto.RegistrationProductDto
import commerce.hosinsa.domain.product.dto.UpdateProductDto
import commerce.hosinsa.domain.product.service.ProductService
import io.mockk.mockk

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

val productService = mockk<ProductService>()