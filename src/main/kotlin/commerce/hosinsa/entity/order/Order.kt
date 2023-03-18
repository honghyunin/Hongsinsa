package commerce.hosinsa.entity.order

import commerce.hosinsa.entity.BaseTimeEntity
import commerce.hosinsa.entity.member.Member
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
class Order(
    @Column(name = "address", nullable = false)
    val address: String,

    @Comment(value = "수령인")
    @Column(name = "recipient", nullable = false)
    val recipient: String,

    @Comment(value = "연락처")
    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Comment(value = "요청사항")
    @Column(name = "request", nullable = true)
    val requestedMessage: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: OrderStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member

) : BaseTimeEntity() {

    @Column(name = "idx")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Int? = null
}