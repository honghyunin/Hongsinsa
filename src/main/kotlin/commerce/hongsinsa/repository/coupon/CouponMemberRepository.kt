package commerce.hongsinsa.repository.coupon

import commerce.hongsinsa.entity.coupon.MemberCoupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponMemberRepository: JpaRepository<MemberCoupon, Int>