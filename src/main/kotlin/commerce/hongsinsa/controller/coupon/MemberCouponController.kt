package commerce.hongsinsa.controller.coupon

import commerce.hongsinsa.dto.coupon.CouponDto
import commerce.hongsinsa.service.member.MemberCouponService
import commerce.hongsinsa.service.member.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/memberCoupon")
class MemberCouponController(
    private val memberCouponService: MemberCouponService,
    private val memberService: MemberService
) {

    @GetMapping("/{memberIdx}")
    fun getCoupons(@PathVariable memberIdx: Int): MutableList<CouponDto> {
        memberService.existsByIdx(memberIdx)

        return memberCouponService.getCoupons(memberIdx)
    }
}