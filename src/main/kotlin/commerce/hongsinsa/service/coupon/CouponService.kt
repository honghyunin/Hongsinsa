package commerce.hongsinsa.service.coupon

import commerce.hongsinsa.repository.coupon.CouponRepository
import commerce.hongsinsa.entity.coupon.Coupon
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CouponService(
    private val couponRepository: CouponRepository
) {
    @Transactional(rollbackFor = [Exception::class])
    fun saveCoupon(coupon: Coupon) {
        couponRepository.save(coupon)
    }
}

