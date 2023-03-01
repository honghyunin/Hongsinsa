package commerce.hosinsa.domain.repository.coupon

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hosinsa.domain.dto.coupon.QResponseCoupon
import commerce.hosinsa.domain.dto.coupon.ResponseCoupon
import commerce.hosinsa.entity.brand.QBrand.brand
import commerce.hosinsa.entity.coupon.CouponMember
import commerce.hosinsa.entity.coupon.QCoupon.coupon
import commerce.hosinsa.entity.coupon.QCouponMember.couponMember
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CouponMemberCustomRepositoryImpl(private val queryFactory: JPAQueryFactory) :
    CouponMemberCustomRepository {

    override fun findByExpiredCoupon(): MutableList<CouponMember> = queryFactory.selectFrom(couponMember)
        .where(couponMember.expiredAt.after(LocalDateTime.now()))
        .fetch()

    override fun findResponseCouponMembersByIdx(idx: Int): MutableList<ResponseCoupon> = queryFactory.select(
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
        .where(couponMember.member.idx.eq(idx))
        .fetch()
}