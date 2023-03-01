package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.service.coupon.CouponMemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/couponMember")
class CouponMemberController(private val couponMemberService: CouponMemberService) {

    @GetMapping("/{memberId}")
    fun getCoupons(@PathVariable memberId: Int) = couponMemberService.getCoupons(memberId)
}