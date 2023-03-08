package commerce.hosinsa.domain.coupon

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify

class CouponServiceTest : DescribeSpec({

    describe("saveCoupon") {

        context("브랜드 이름이 존재할 경우") {
            every { couponService.saveCoupon(SAVE_COUPON_DTO) } just Runs

            if(SAVE_COUPON_DTO.brandName != null)
                couponService.saveCoupon(SAVE_COUPON_DTO)
            it("브랜드 전용 쿠폰이 발급된다") {
                verify(exactly = 1) { couponService.saveCoupon(SAVE_COUPON_DTO) }
            }
        }

        context("브랜드 이름이 존재하지 않을 경우") {
            every { couponService.saveCoupon(NOT_BRAND_SAVE_COUPON_DTO) } just Runs

            if(NOT_BRAND_SAVE_COUPON_DTO.brandName == null)
                couponService.saveCoupon(NOT_BRAND_SAVE_COUPON_DTO)
            it("브랜드가 없는 쿠폰이 발급된다") {
                verify(exactly = 1) { couponService.saveCoupon(NOT_BRAND_SAVE_COUPON_DTO) }
            }
        }
    }
})