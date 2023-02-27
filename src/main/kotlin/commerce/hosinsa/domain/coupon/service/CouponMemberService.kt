package commerce.hosinsa.domain.coupon.service

import commerce.hosinsa.domain.coupon.dto.ResponseCoupon

interface CouponMemberService {
    fun getCoupons(memberId: Int): MutableList<ResponseCoupon>
}