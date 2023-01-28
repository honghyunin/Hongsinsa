package commerce.hosinsa.domain.member.entity

import commerce.hosinsa.domain.baseTime.entity.BaseTimeEntity
import commerce.hosinsa.domain.cart.entity.Cart
import commerce.hosinsa.domain.coupon.entity.Coupon
import commerce.hosinsa.domain.order.entity.Order
import javax.persistence.*
import java.time.LocalDateTime

@Entity
class Member(

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "id", nullable = false)
    val id: String,

    @Column(name = "pw", nullable = false)
    var pw: String,

    @Column(name = "name", nullable = false)
    var name: String, // 실명

    @Column(name = "nickname", nullable = true)
    var nickname: String? = null, // 닉네임

    @Column(name = "weight", nullable = true)
    var weight: Short? = null,

    @Column(name = "height", nullable = true)
    var height: Short? = null,

    @Column(name = "age", nullable = true)
    val age: Short? = null,

    @Column(name = "phone_number", nullable = true)
    var phoneNumber: Short? = null,

    @Column(name = "gender", nullable = true)
    val gender: Char? = null,

    @Column(name = "address", nullable = true)
    var address: String? = null,

    @Column(name = "birthday", nullable = true)
    val birthday: LocalDateTime? = null,

    @Column(name = "role", nullable = false) @Enumerated(EnumType.STRING)
    val role: Role

) : BaseTimeEntity() {

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Int? = null

    @OneToOne(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    @JoinColumn(name = "cart_id")
    val cart: Cart? = null

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    val coupon: MutableList<Coupon> = mutableListOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    val order: MutableList<Order> = mutableListOf()

}