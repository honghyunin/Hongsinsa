package commerce.hosinsa.global.extension

import commerce.hosinsa.domain.dto.product.ProductOptionResponse
import commerce.hosinsa.domain.dto.product.ProductResponse
import commerce.hosinsa.domain.dto.product.RegistrationProductDto
import commerce.hosinsa.domain.dto.product.UpdateProductDto
import commerce.hosinsa.entity.brand.Brand
import commerce.hosinsa.entity.product.Product


fun RegistrationProductDto.toProduct(brand: Brand) = Product(
    name = this.name,
    price = this.price,
    category = this.category,
    gender = this.gender,
    stock = this.stock,
    brand = brand,
)

fun Product.updateProduct(updateProductDto: UpdateProductDto) {
    this.name = updateProductDto.name
    this.price = updateProductDto.price
    this.category = updateProductDto.category
    this.gender = updateProductDto.gender
}

fun Product.soldOut() {
    this.isSoldOut = true
}

fun Product.toProductResponse(options: MutableList<ProductOptionResponse>): ProductResponse = ProductResponse(
    productId = idx!!,
    name = name,
    price = price,
    category = category,
    gender = gender,
    brand = brand.name,
    options = options
).let { product ->
    product.options = options
    product
}

