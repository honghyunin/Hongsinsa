package commerce.hosinsa.domain.service.coupon

import commerce.hosinsa.domain.dto.coupon.ResponseCoupon


interface CouponMemberService {
    fun getCoupons(memberId: Int): MutableList<ResponseCoupon>
}