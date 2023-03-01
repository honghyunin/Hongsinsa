package commerce.hosinsa.domain.repository.coupon

import commerce.hosinsa.dto.coupon.ResponseCoupon
import commerce.hosinsa.entity.coupon.CouponMember

interface CouponMemberCustomRepository {
    fun findByExpiredCoupon(): MutableList<CouponMember>
    fun findResponseCouponMembersByMemberId(memberId: Int): MutableList<ResponseCoupon>
}