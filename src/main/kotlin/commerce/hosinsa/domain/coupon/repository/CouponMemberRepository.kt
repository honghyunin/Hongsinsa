package commerce.hosinsa.domain.coupon.repository

import commerce.hosinsa.domain.coupon.entity.CouponMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponMemberRepository: JpaRepository<CouponMember, Int>