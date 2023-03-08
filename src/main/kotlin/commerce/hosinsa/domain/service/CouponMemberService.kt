package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.coupon.ResponseCoupon
import commerce.hosinsa.domain.repository.CouponMemberCustomRepository
import commerce.hosinsa.domain.repository.MemberRepository
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class CouponMemberService(
    private val couponMemberCustomRepository: CouponMemberCustomRepository,
    private val memberRepository: MemberRepository
) {
    fun getCoupons(memberIdx: Int): MutableList<ResponseCoupon> {
        if (!memberRepository.existsById(memberIdx))
            throw CustomException(MEMBER_NOT_FOUND)

        return couponMemberCustomRepository.findResponseCouponMembersByIdx(memberIdx)
    }
}