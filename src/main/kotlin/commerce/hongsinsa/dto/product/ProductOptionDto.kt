package commerce.hongsinsa.dto.product

import commerce.hongsinsa.entity.product.ProductOption
import commerce.hongsinsa.entity.product.ProductSize
import io.swagger.v3.oas.annotations.Parameter

class ProductOptionDto (
    val productOptionIdx: Int?,
    val size: ProductSize,
    val color: String
) {
    constructor(productOption: ProductOption) : this(
        productOptionIdx = productOption.idx,
        size = productOption.size,
        color = productOption.color
    )
}