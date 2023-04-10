package commerce.hongsinsa.dto.order

import com.querydsl.core.annotations.QueryProjection
import commerce.hongsinsa.entity.order.OrderStatus
import commerce.hongsinsa.entity.product.ProductSize
import java.time.LocalDateTime

class GetOrderDto @QueryProjection constructor(
    val orderIdx: Int,
    val productName: String,
    val brandName: String,
    val productIdx: Int,
    val orderCreatedAt: LocalDateTime,
    val quantity: Byte,
    val price: Int,
    val size: ProductSize,
    val color: String,
    var status: OrderStatus
)
