package commerce.hongsinsa.global.extension

import commerce.hongsinsa.domain.dto.coupon.SaveCouponDto
import commerce.hongsinsa.entity.brand.Brand
import commerce.hongsinsa.entity.coupon.Coupon

fun SaveCouponDto.toCoupon(brand: Brand?) = Coupon(
    name = name,
    discount = discount,
    brand = brand
)