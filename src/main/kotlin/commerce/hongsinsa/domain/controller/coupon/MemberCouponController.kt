package commerce.hongsinsa.domain.controller.coupon

import commerce.hongsinsa.domain.dto.coupon.ResponseCoupon
import commerce.hongsinsa.domain.service.CouponMemberService
import commerce.hongsinsa.domain.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/couponMember")
class CouponMemberController(
    private val couponMemberService: CouponMemberService,
    private val memberService: MemberService
) {

    @GetMapping("/{memberIdx}")
    fun getCoupons(@PathVariable memberIdx: Int): MutableList<ResponseCoupon> {
        memberService.existsByIdx(memberIdx)

        return couponMemberService.getCoupons(memberIdx)
    }
}