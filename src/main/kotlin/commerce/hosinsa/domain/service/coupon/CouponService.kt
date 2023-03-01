package commerce.hosinsa.domain.service.coupon

import commerce.hosinsa.dto.coupon.SaveCouponDto

interface CouponService {
    fun saveCoupon(saveCouponDto: SaveCouponDto)
}