package commerce.hosinsa.domain.coupon

import commerce.hosinsa.domain.brand.BRAND
import commerce.hosinsa.domain.dto.coupon.ResponseCoupon
import commerce.hosinsa.domain.dto.coupon.SaveCouponDto
import commerce.hosinsa.domain.product.BRAND_NAME
import commerce.hosinsa.domain.repository.BrandRepository
import commerce.hosinsa.domain.repository.CouponMemberCustomRepository
import commerce.hosinsa.domain.repository.CouponRepository
import commerce.hosinsa.domain.repository.MemberRepository
import commerce.hosinsa.domain.service.CouponMemberService
import commerce.hosinsa.domain.service.CouponService
import commerce.hosinsa.entity.coupon.Coupon
import io.mockk.mockk
import java.time.LocalDateTime

val NAME = "COUPON"
val STATUS = 'V'
val DISCOUNT = (10).toByte()

val NOT_BRAND_SAVE_COUPON_DTO = SaveCouponDto(
    name = NAME,
    discount = DISCOUNT,
    status = STATUS,
    brandName = null
)

val SAVE_COUPON_DTO = SaveCouponDto(
    name = NAME,
    discount = DISCOUNT,
    status = STATUS,
    brandName = BRAND_NAME
)

val RESPONSE_COUPON = ResponseCoupon(
    couponName = NAME,
    discount = DISCOUNT,
    brandName = BRAND_NAME,
    assignedAt = LocalDateTime.now(),
    expiredAt = LocalDateTime.now()
)
val COUPON = Coupon(
    name = NAME,
    discount = DISCOUNT,
    brand = BRAND
)

val BRAND_NULL_COUPON = Coupon(
    name = NAME,
    discount = DISCOUNT,
    brand = null
)

val RESPONSE_COUPON_LIST = mutableListOf(RESPONSE_COUPON, RESPONSE_COUPON, RESPONSE_COUPON)

val couponMemberService = mockk<CouponMemberService>()
val couponMemberCustomRepository = mockk<CouponMemberCustomRepository>()
val memberRepository = mockk<MemberRepository>()
val brandRepository = mockk<BrandRepository>()
val couponRepository = mockk<CouponRepository>()
val couponService = mockk<CouponService>()