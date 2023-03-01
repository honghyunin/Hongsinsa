package commerce.hosinsa.domain.repository.coupon

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hosinsa.domain.brand.entity.QBrand.brand
import commerce.hosinsa.domain.coupon.dto.QResponseCoupon
import commerce.hosinsa.dto.coupon.ResponseCoupon
import commerce.hosinsa.entity.coupon.CouponMember
import commerce.hosinsa.domain.coupon.entity.QCoupon.coupon
import commerce.hosinsa.domain.coupon.entity.QCouponMember.couponMember
import commerce.hosinsa.entity.member.Member
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CouponMemberCustomRepositoryImpl(private val queryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(Member::class.java), CouponMemberCustomRepository {

    override fun findByExpiredCoupon(): MutableList<CouponMember> = queryFactory.selectFrom(couponMember)
            .where(couponMember.expiredAt.after(LocalDateTime.now()))
            .fetch()

    override fun findResponseCouponMembersByMemberId(memberId: Int): MutableList<ResponseCoupon> = queryFactory.select(
            QResponseCoupon(
            coupon.name,
            coupon.discount,
            coupon.brand.name,
            couponMember.assignedAt,
            couponMember.expiredAt
        )
        )
            .from(couponMember)
            .innerJoin(couponMember.coupon, coupon)
            .leftJoin(coupon.brand, brand)
            .where(couponMember.member.memberId.eq(memberId))
            .fetch()
}