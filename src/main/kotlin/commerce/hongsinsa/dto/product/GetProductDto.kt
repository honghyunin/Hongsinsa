package commerce.hongsinsa.dto.product

import com.querydsl.core.annotations.QueryProjection

class GetProductDto @QueryProjection constructor(
    val productId: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val brand: String?,
    var options: MutableList<ProductOptionResponse>
)