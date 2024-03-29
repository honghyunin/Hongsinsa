package commerce.hongsinsa.dto.coupon

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class SaveCouponDto(
    val name: String,
    val discount: Byte,
    @Schema(example = "V")
    val status: Char,
    val brandName: String? = null
) {
    var assignedAt: LocalDateTime? = null
    var expiredAt: LocalDateTime? = null

    init {
        assignedAt = LocalDateTime.now()
        expiredAt = assignedAt!!.plusDays(14)
    }
}