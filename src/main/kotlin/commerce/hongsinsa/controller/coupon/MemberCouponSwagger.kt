package commerce.hongsinsa.controller.coupon

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "회원쿠폰관리", description = "회원 쿠폰 API")
interface MemberCouponSwagger {

    @Operation(summary = "보유 쿠폰 전체 조회", description = "보유 중인 쿠폰을 전체 조회")
    fun getCoupons(memberIdx: Int): ResponseEntity<Any>
}