package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.dto.coupon.CouponDto
import commerce.hongsinsa.domain.repository.coupon.CouponMemberCustomRepository
import org.springframework.stereotype.Service

@Service
class MemberCouponService(
    private val couponMemberCustomRepository: CouponMemberCustomRepository,
) {
    fun getCoupons(memberIdx: Int): MutableList<CouponDto> {
        return couponMemberCustomRepository.findResponseCouponMembersByIdx(memberIdx)
    }
}