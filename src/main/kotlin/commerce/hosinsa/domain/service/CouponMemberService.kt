package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.coupon.ResponseCoupon
import commerce.hosinsa.domain.repository.CouponMemberCustomRepository
import org.springframework.stereotype.Service

@Service
class CouponMemberService(
    private val couponMemberCustomRepository: CouponMemberCustomRepository,
) {
    fun getCoupons(memberIdx: Int): MutableList<ResponseCoupon> {
        return couponMemberCustomRepository.findResponseCouponMembersByIdx(memberIdx)
    }
}