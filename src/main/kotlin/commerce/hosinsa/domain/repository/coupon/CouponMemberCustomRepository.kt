package commerce.hosinsa.domain.repository.coupon

import commerce.hosinsa.domain.dto.coupon.ResponseCoupon
import commerce.hosinsa.entity.coupon.CouponMember

interface CouponMemberCustomRepository {
    fun findByExpiredCoupon(): MutableList<CouponMember>
    fun findResponseCouponMembersByIdx(idx: Int): MutableList<ResponseCoupon>
}