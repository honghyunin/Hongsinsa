package commerce.hosinsa.domain.coupon.entity

import commerce.hosinsa.domain.member.entity.Member
import javax.persistence.*

@Entity
class CouponMember(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_member_id")
    val couponMemberId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    val coupon: Coupon,
)