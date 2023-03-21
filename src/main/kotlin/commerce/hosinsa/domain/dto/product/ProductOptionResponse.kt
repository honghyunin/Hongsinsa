package commerce.hosinsa.domain.dto.product

import com.querydsl.core.annotations.QueryProjection
import commerce.hosinsa.entity.product.ProductSize

class ProductOptionResponse @QueryProjection constructor (
    val productIdx: Int,
    val color: String,
    val size: ProductSize,
)