package commerce.hongsinsa.dto.product

import com.querydsl.core.annotations.QueryProjection
import commerce.hongsinsa.entity.product.ProductSize

class ProductOptionResponse @QueryProjection constructor (
    val productIdx: Int,
    val color: String,
    val size: ProductSize,
)