package commerce.hosinsa.domain.coupon.entity

import commerce.hosinsa.domain.member.entity.Member
import java.time.LocalDateTime
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

    @Column(name = "assigned_at", nullable = false)
    val assignedAt: LocalDateTime? = null,

    @Column(name = "expired_at", nullable = false)
    var expiredAt: LocalDateTime? = null,

    @Column(name = "status", nullable = false)
    var status: Char, // E(expired or Valid),

    @Column(name = "is_delete", nullable = false)
    var isDelete: Boolean = false,
)