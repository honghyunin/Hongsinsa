package commerce.hosinsa.domain.cart.entity

import commerce.hosinsa.domain.baseTime.entity.BaseTimeEntity
import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.product.entity.Product
import javax.persistence.*

@Entity
class Cart(

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product

): BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartId: Int? = null

}