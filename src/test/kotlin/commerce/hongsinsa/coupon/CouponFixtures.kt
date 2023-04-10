package commerce.hongsinsa.coupon

import commerce.hongsinsa.brand.BRAND
import commerce.hongsinsa.dto.coupon.CouponDto
import commerce.hongsinsa.dto.coupon.SaveCouponDto
import commerce.hongsinsa.product.BRAND_NAME
import commerce.hongsinsa.repository.brand.BrandRepository
import commerce.hongsinsa.repository.coupon.CouponMemberQueryRepository
import commerce.hongsinsa.repository.coupon.CouponRepository
import commerce.hongsinsa.repository.member.MemberRepository
import commerce.hongsinsa.service.coupon.CouponService
import commerce.hongsinsa.service.member.MemberCouponService
import commerce.hongsinsa.entity.coupon.Coupon
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

val RESPONSE_COUPON = CouponDto(
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

val memberCouponService = mockk<MemberCouponService>()
val couponMemberQueryRepository = mockk<CouponMemberQueryRepository>()
val memberRepository = mockk<MemberRepository>()
val brandRepository = mockk<BrandRepository>()
val couponRepository = mockk<CouponRepository>()
val couponService = mockk<CouponService>()