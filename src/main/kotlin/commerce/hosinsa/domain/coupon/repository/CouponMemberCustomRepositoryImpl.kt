package commerce.hosinsa.domain.coupon.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hosinsa.domain.coupon.entity.QCouponMember.couponMember
import commerce.hosinsa.domain.member.entity.Member
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CouponMemberCustomRepositoryImpl(private val queryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(Member::class.java), CouponMemberCustomRepository {

    override fun findByExpiredCoupon() = queryFactory.selectFrom(couponMember)
            .where(couponMember.expiredAt.after(LocalDateTime.now()))
            .fetch().toMutableList()
}