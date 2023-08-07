package commerce.hongsinsa.entity.cart

import commerce.hongsinsa.entity.BaseTimeEntity
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.product.Product
import javax.persistence.*

@Entity
class Cart(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product

): BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Int? = null

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false
}