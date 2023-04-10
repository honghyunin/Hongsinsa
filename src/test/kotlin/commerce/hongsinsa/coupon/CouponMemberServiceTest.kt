package commerce.hongsinsa.coupon

import commerce.hongsinsa.member.MEMBER_IDX
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.MEMBER_NOT_FOUND
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.verify

class CouponMemberServiceTest : DescribeSpec({

    describe("getCoupons") {

        context("존재하는 회원이 입력될 경우") {
            every { memberRepository.existsById(MEMBER_IDX) } returns true
            every { memberCouponService.getCoupons(MEMBER_IDX) } returns RESPONSE_COUPON_LIST

            memberCouponService.getCoupons(MEMBER_IDX)
            it("보유한 쿠폰 조회에 성공한다") {
                verify(exactly = 1) { memberCouponService.getCoupons(MEMBER_IDX) }
            }
        }

        context("존재하지 않는 회원이 입력될 경우") {
            every { memberRepository.existsById(MEMBER_IDX) } returns false
            every { memberCouponService.getCoupons(MEMBER_IDX) } throws CustomException(MEMBER_NOT_FOUND)

            it("Member Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { memberCouponService.getCoupons(MEMBER_IDX) }
            }
        }
    }
})