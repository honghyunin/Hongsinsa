package commerce.hongsinsa.dto.coupon

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class CouponDto @QueryProjection constructor(
    val couponName: String,
    val discount: Byte,
    val brandName: String?,
    val assignedAt: LocalDateTime,
    val expiredAt: LocalDateTime,
)
