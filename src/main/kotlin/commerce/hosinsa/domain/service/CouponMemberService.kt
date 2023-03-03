package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.coupon.ResponseCoupon
import commerce.hosinsa.domain.repository.CouponMemberCustomRepository
import commerce.hosinsa.domain.repository.MemberRepository
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import org.springframework.stereotype.Service

@Service
class CouponMemberService(
    private val couponMemberCustomRepository: CouponMemberCustomRepository,
    private val memberRepository: MemberRepository
) {
    fun getCoupons(memberId: Int): MutableList<ResponseCoupon> {
        if (memberRepository.existsById(memberId))
            throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

        return couponMemberCustomRepository.findResponseCouponMembersByIdx(memberId)
    }
}