package commerce.hosinsa.domain.product.dto

import com.querydsl.core.annotations.QueryProjection

class ProductResponse @QueryProjection constructor(
    val productId: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val brand: String
)
