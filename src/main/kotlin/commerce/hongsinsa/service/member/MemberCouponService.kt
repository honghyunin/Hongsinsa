package commerce.hongsinsa.service.member

import commerce.hongsinsa.dto.coupon.CouponDto
import commerce.hongsinsa.repository.coupon.CouponMemberQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCouponService(
    private val couponMemberQueryRepository: CouponMemberQueryRepository,
) {
    @Transactional(readOnly = true)
    fun getCoupons(memberIdx: Int): MutableList<CouponDto> =
        couponMemberQueryRepository.findResponseMemberCouponsByIdx(memberIdx)
}