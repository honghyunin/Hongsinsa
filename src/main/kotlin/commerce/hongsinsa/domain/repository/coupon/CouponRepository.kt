package commerce.hongsinsa.domain.repository.coupon

import commerce.hongsinsa.entity.coupon.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository: JpaRepository<Coupon, Int> {
    fun findByName(name: String): Coupon?
}