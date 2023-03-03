package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.coupon.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository: JpaRepository<Coupon, Int> {
    fun findByName(name: String): Coupon?
}