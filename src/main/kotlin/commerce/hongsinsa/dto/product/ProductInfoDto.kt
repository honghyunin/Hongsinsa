package commerce.hongsinsa.dto.product

import com.querydsl.core.annotations.QueryProjection

class ProductInfoDto @QueryProjection constructor(
    val productIdx: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val brand: String,
)