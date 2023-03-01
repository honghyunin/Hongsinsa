package commerce.hosinsa.domain.service.coupon

import commerce.hosinsa.dto.coupon.ResponseCoupon

interface CouponMemberService {
    fun getCoupons(memberId: Int): MutableList<ResponseCoupon>
}