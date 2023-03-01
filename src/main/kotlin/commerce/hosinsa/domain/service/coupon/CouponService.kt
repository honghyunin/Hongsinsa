package commerce.hosinsa.domain.service.coupon

import commerce.hosinsa.domain.dto.coupon.SaveCouponDto

interface CouponService {
    fun saveCoupon(saveCouponDto: SaveCouponDto)
}