package commerce.hongsinsa.domain.service.member

import commerce.hongsinsa.domain.dto.coupon.CouponDto
import commerce.hongsinsa.domain.repository.coupon.CouponMemberQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCouponService(
    private val couponMemberQueryRepository: CouponMemberQueryRepository,
) {
    @Transactional(readOnly = true)
    fun getCoupons(memberIdx: Int): MutableList<CouponDto> {
        return couponMemberQueryRepository.findResponseMemberCouponsByIdx(memberIdx)
    }
}