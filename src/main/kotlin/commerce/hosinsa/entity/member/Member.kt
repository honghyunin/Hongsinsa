package commerce.hosinsa.entity.member

import commerce.hosinsa.entity.BaseTimeEntity
import commerce.hosinsa.entity.cart.Cart
import commerce.hosinsa.entity.coupon.CouponMember
import commerce.hosinsa.entity.order.Order
import org.hibernate.annotations.DynamicUpdate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.stream.Collectors
import javax.persistence.*

@DynamicUpdate
@Entity
class Member(

    @Column(name = "email", nullable = false, length = 50)
    var email: String,

    @Column(name = "id", nullable = false, length = 20, unique = true)
    val id: String,

    @Column(name = "pw", nullable = false, length = 100)
    var pw: String,

    @Column(name = "name", nullable = false, length = 10)
    var name: String, // 실명

    @Column(name = "nickname", nullable = false, length = 24)
    var nickname: String, // 닉네임

    @Column(name = "weight", nullable = true)
    var weight: Short? = null,

    @Column(name = "height", nullable = true)
    var height: Short? = null,

    @Column(name = "age", nullable = true)
    val age: Short? = null,

    @Column(name = "phone_number", nullable = false, length = 14)
    var phoneNumber: String,

    @Column(name = "gender", nullable = false, length = 1)
    val gender: Char,

    @Column(name = "address", nullable = true, length = 100)
    var address: String? = null,

    @Column(name = "birthday", nullable = false)
    val birthday: LocalDate,

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    @Column(name = "role", nullable = false) @Enumerated(EnumType.STRING)
    var roles: MutableList<Role>

) : BaseTimeEntity(), UserDetails {

    @Column(name = "idx")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Int? = null

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    val cart: MutableList<Cart> = mutableListOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member"
    )
    val couponMember: MutableList<CouponMember> = mutableListOf()

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