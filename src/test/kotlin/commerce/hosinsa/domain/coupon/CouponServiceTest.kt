package commerce.hosinsa.domain.coupon

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify

class CouponServiceTest : DescribeSpec({

    describe("saveCoupon") {

        context("브랜드 이름이 존재할 경우") {
            every { couponService.saveCoupon(COUPON) } just Runs

            if(COUPON.brand != null)
                couponService.saveCoupon(COUPON)
            it("브랜드 전용 쿠폰이 발급된다") {
                verify(exactly = 1) { couponService.saveCoupon(COUPON) }
            }
        }

        context("브랜드 이름이 존재하지 않을 경우") {
            every { couponService.saveCoupon(BRAND_NULL_COUPON) } just Runs

            if(BRAND_NULL_COUPON.brand == null)
                couponService.saveCoupon(BRAND_NULL_COUPON)
            it("브랜드가 없는 쿠폰이 발급된다") {
                verify(exactly = 1) { couponService.saveCoupon(BRAND_NULL_COUPON) }
            }
        }
    }
})