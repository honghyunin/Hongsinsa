package commerce.hongsinsa.domain.repository.coupon

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.domain.dto.coupon.CouponDto
import commerce.hongsinsa.domain.dto.coupon.QCouponDto
import commerce.hongsinsa.entity.brand.QBrand.brand
import commerce.hongsinsa.entity.coupon.MemberCoupon
import commerce.hongsinsa.entity.coupon.QCoupon.coupon
import commerce.hongsinsa.entity.coupon.QMemberCoupon.memberCoupon
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CouponMemberQueryRepository(private val queryFactory: JPAQueryFactory) {

    fun findByExpiredCoupon(): MutableList<MemberCoupon> = queryFactory.selectFrom(memberCoupon)
        .where(memberCoupon.expiredAt.after(LocalDateTime.now()))
        .fetch()

    fun findResponseMemberCouponsByIdx(idx: Int): MutableList<CouponDto> = queryFactory.select(
        QCouponDto(
            coupon.name,
            coupon.discount,
            coupon.brand.name,
            memberCoupon.assignedAt,
            memberCoupon.expiredAt
        )
    )
        .from(memberCoupon)
        .innerJoin(memberCoupon.coupon, coupon)
        .leftJoin(coupon.brand, brand)
        .where(memberCoupon.member.idx.eq(idx))
        .fetch()
}