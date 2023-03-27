package commerce.hongsinsa.domain.repository.coupon

import commerce.hongsinsa.entity.coupon.CouponMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponMemberRepository: JpaRepository<CouponMember, Int>