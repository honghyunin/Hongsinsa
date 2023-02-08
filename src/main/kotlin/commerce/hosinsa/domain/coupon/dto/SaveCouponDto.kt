package commerce.hosinsa.domain.coupon.dto

import java.time.LocalDateTime

class SaveCouponDto(
    val name: String,
    val discount: Short,
    var assignedAt: LocalDateTime? = null,
    var expiredAt: LocalDateTime? = null,
    val status: Char,
    val brandName: String? = null
)
