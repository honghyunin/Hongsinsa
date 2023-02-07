package commerce.hosinsa.domain.coupon.entity

import commerce.hosinsa.domain.member.entity.Member
import javax.persistence.*
import java.time.LocalDateTime

@Entity
class Coupon(

    @Column(name = "name", nullable = false)
    val name: String? = null,

    @Column(name = "discount", nullable = false)
    val discount: Short? = null,

    @Column(name = "assigned_at", nullable = false)
    val assignedAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "expired_at", nullable = false)
    val expiredAt: LocalDateTime? = null,

    @Column(name = "status", nullable = false)
    val status: Char, // E(expired or Valid)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    val member: Member? = null

) {

    @Column(name = "coupon_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val couponId: Int? = null

}