package commerce.hosinsa.domain.coupon.repository

import commerce.hosinsa.domain.coupon.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository: JpaRepository<Coupon, Int> {
}