package commerce.hosinsa.domain.dto.cart

import com.querydsl.core.annotations.QueryProjection

class CartResponse @QueryProjection constructor(
    val name: String,
    val price: Int,
    var quantity: Int,
    var orderPrice: Int,
)
