package commerce.hongsinsa.domain.repository

import commerce.hongsinsa.domain.dto.coupon.ResponseCoupon
import commerce.hongsinsa.entity.coupon.CouponMember

interface CouponMemberCustomRepository {
    fun findByExpiredCoupon(): MutableList<CouponMember>
    fun findResponseCouponMembersByIdx(idx: Int): MutableList<ResponseCoupon>
}