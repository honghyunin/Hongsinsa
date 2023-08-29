package commerce.hongsinsa.controller.coupon

import commerce.hongsinsa.dto.coupon.CouponDto
import commerce.hongsinsa.service.member.MemberCouponService
import commerce.hongsinsa.service.member.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/memberCoupons")
class MemberCouponController(
    private val memberCouponService: MemberCouponService,
    private val memberService: MemberService
) : MemberCouponSwagger {

    @GetMapping("/{memberIdx}")
    override fun getCoupons(@PathVariable memberIdx: Int): ResponseEntity<Any> {
        memberService.existsByIdx(memberIdx)

        val coupons = memberCouponService.getCoupons(memberIdx)

        return ResponseEntity.ok()
            .body(coupons)
    }
}