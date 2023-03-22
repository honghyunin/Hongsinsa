package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.repository.CouponRepository
import commerce.hongsinsa.entity.coupon.Coupon
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository
) {
    fun saveCoupon(coupon: Coupon) {
        couponRepository.save(coupon)
    }

}

