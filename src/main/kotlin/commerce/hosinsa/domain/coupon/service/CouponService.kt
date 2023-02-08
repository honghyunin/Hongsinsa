package commerce.hosinsa.domain.coupon.service

import commerce.hosinsa.domain.coupon.dto.SaveCouponDto

interface CouponService {
    fun saveCoupon(saveCouponDto: SaveCouponDto)
}