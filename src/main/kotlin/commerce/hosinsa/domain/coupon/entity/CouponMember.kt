package commerce.hosinsa.domain.coupon.entity

import commerce.hosinsa.domain.member.entity.Member
import javax.persistence.*

@IdClass(value = CouponMemberId::class)
@Entity
class CouponMember(

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    val member: Member? = null,

    @Id
    @Column(name = "coupon_member_id")
    val couponMemberId: CouponMemberId
)