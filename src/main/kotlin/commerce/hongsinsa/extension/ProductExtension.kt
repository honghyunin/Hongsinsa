package commerce.hongsinsa.extension

import commerce.hongsinsa.dto.product.ProductOptionResponse
import commerce.hongsinsa.dto.product.GetProductDto
import commerce.hongsinsa.dto.product.RegistrationProductDto
import commerce.hongsinsa.dto.product.UpdateProductDto
import commerce.hongsinsa.entity.brand.Brand
import commerce.hongsinsa.entity.product.Product


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

fun Product.toProductResponse(options: MutableList<ProductOptionResponse>): GetProductDto = GetProductDto(
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

