package commerce.hosinsa.domain.dto.order

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class GetOrderResponse @QueryProjection constructor(
    val orderIdx: Int,
    val productName: String,
    val brandName: String,
    val productIdx: Int,
    val orderCreatedAt: LocalDateTime,
    val quantity: Byte,
    val price: Int
)
