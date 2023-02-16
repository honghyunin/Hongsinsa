package commerce.hosinsa.domain.coupon.dto

import java.time.LocalDateTime

class SaveCouponDto(
    val name: String,
    val discount: Short,
    val status: Char,
    val brandName: String? = null
) {
    var assignedAt: LocalDateTime = LocalDateTime.now()
    var expiredAt: LocalDateTime? = null
}