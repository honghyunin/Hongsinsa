package commerce.hosinsa.domain.dto.coupon

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class ResponseCoupon @QueryProjection constructor(
    val couponName: String,
    val discount: Short,
    val brandName: String?,
    val assignedAt: LocalDateTime,
    val expiredAt: LocalDateTime,
)
