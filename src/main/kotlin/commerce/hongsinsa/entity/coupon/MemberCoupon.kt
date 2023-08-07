package commerce.hongsinsa.entity.coupon

import commerce.hongsinsa.entity.member.Member
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class MemberCoupon(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    val coupon: Coupon,
) {

    @Column(name = "assigned_at", nullable = false)
    lateinit var assignedAt: LocalDateTime

    @Column(name = "expired_at", nullable = false)
    lateinit var expiredAt: LocalDateTime

    @Column(name = "status", nullable = false)
    var status: Char = 'V' // E(expired or Valid),

    @Column(name = "is_delete", nullable = false)
    var isDelete: Boolean = false

    init {
        val day = LocalDateTime.now()

        assignedAt = day
        expiredAt = day.plusDays(14)
    }
}