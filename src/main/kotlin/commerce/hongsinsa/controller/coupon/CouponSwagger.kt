package commerce.hongsinsa.controller.coupon

import commerce.hongsinsa.dto.coupon.SaveCouponDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "쿠폰관리", description = "쿠폰 관리 API")
interface CouponSwagger {

    @Operation(summary = "쿠폰 발급", description = "쿠폰 발급")
    fun saveCoupon(saveCouponDto: SaveCouponDto) : ResponseEntity<Any>
}