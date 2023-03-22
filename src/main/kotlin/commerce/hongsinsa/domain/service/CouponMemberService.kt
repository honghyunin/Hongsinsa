package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.dto.coupon.ResponseCoupon
import commerce.hongsinsa.domain.repository.CouponMemberCustomRepository
import org.springframework.stereotype.Service

@Service
class CouponMemberService(
    private val couponMemberCustomRepository: CouponMemberCustomRepository,
) {
    fun getCoupons(memberIdx: Int): MutableList<ResponseCoupon> {
        return couponMemberCustomRepository.findResponseCouponMembersByIdx(memberIdx)
    }
}