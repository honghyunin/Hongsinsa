package commerce.hongsinsa.domain.repository.coupon

import commerce.hongsinsa.domain.dto.coupon.ResponseCoupon
import commerce.hongsinsa.entity.coupon.MemberCoupon

interface CouponMemberCustomRepository {
    fun findByExpiredCoupon(): MutableList<MemberCoupon>
    fun findResponseCouponMembersByIdx(idx: Int): MutableList<ResponseCoupon>
}