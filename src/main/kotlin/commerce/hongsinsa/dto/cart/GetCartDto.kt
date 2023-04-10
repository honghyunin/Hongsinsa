package commerce.hongsinsa.dto.cart

import com.querydsl.core.annotations.QueryProjection

class GetCartDto @QueryProjection constructor(
    val name: String,
    val price: Int,
    var quantity: Int,
    var orderPrice: Int,
)
