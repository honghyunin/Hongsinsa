package commerce.hongsinsa.dto.request

import com.querydsl.core.annotations.QueryProjection
import commerce.hongsinsa.entity.request.RequestStatus
import commerce.hongsinsa.entity.product.ProductSize
import java.time.LocalDateTime

class GetRequestDto @QueryProjection constructor(
    val requestIdx: Int,
    val productName: String,
    val brandName: String,
    val productIdx: Int,
    val requestCreatedAt: LocalDateTime,
    val quantity: Byte,
    val price: Int,
    val size: ProductSize,
    val color: String,
    var status: RequestStatus
)
