package commerce.hosinsa.domain.member.entity

import commerce.hosinsa.domain.baseTime.entity.BaseTimeEntity
import commerce.hosinsa.domain.cart.entity.Cart
import commerce.hosinsa.domain.coupon.entity.CouponMember
import commerce.hosinsa.domain.order.entity.Order
import org.hibernate.annotations.DynamicUpdate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import java.time.LocalDateTime
import java.util.stream.Collectors

@DynamicUpdate
@Entity
class Member(

    @Column(name = "email", nullable = false, length = 50)
    var email: String,

    @Column(name = "id", nullable = false, length = 20, unique =  true)
    val id: String,

    @Column(name = "pw", nullable = false, length = 100)
    var pw: String,

    @Column(name = "name", nullable = false, length = 10)
    var name: String, // 실명

    @Column(name = "nickname", nullable = true, length = 24)
    var nickname: String? = null, // 닉네임

    @Column(name = "weight", nullable = true)
    var weight: Short? = null,

    @Column(name = "height", nullable = true)
    var height: Short? = null,

    @Column(name = "age", nullable = true)
    val age: Short? = null,

    @Column(name = "phone_number", nullable = true, length = 14)
    var phoneNumber: String? = null,

    @Column(name = "gender", nullable = true, length = 1)
    val gender: Char? = null,

    @Column(name = "address", nullable = true, length = 100)
    var address: String? = null,

    @Column(name = "birthday", nullable = true)
    val birthday: LocalDateTime? = null,

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    @Column(name = "role", nullable = false) @Enumerated(EnumType.STRING)
    var roles: MutableList<Role>

) : BaseTimeEntity(), UserDetails {

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Int? = null

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    val cart: MutableList<Cart> = mutableListOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    val couponMemberId: MutableList<CouponMember> = mutableListOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    val order: MutableList<Order> = mutableListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.stream().map { role -> SimpleGrantedAuthority("ROLE_$role") }.collect(
            Collectors.toList()
        )

    override fun getPassword(): String = this.pw

    override fun getUsername(): String = this.id

    override fun isAccountNonExpired(): Boolean = this.isAccountNonExpired

    override fun isAccountNonLocked(): Boolean = this.isAccountNonLocked

    override fun isCredentialsNonExpired(): Boolean = this.isCredentialsNonExpired

    override fun isEnabled(): Boolean = this.isEnabled

}