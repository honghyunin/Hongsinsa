package commerce.hosinsa.domain.service.coupon

import commerce.hosinsa.domain.dto.coupon.ResponseCoupon
import commerce.hosinsa.domain.repository.coupon.CouponMemberCustomRepository
import commerce.hosinsa.domain.repository.member.MemberRepository
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class CouponMemberServiceImpl(
    private val couponMemberCustomRepository: CouponMemberCustomRepository,
    private val memberRepository: MemberRepository
) : CouponMemberService {

    override fun getCoupons(memberId: Int): MutableList<ResponseCoupon> {
        if (memberRepository.existsById(memberId))
            throw CustomException(MEMBER_NOT_FOUND)

        return couponMemberCustomRepository.findResponseCouponMembersByIdx(memberId)
    }

}