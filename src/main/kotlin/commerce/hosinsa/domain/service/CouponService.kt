package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.repository.CouponRepository
import commerce.hosinsa.entity.coupon.Coupon
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository
) {
    fun saveCoupon(coupon: Coupon) {
        couponRepository.save(coupon)
    }

}

