package commerce.hosinsa.domain.order.entity

import commerce.hosinsa.domain.baseTime.entity.BaseTimeEntity
import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.product.entity.Product
import javax.persistence.*

@Entity
class Order(
    @Column(name = "total_price", nullable = false)
    val totalPrice: Int,

    @Column(name = "address", nullable = false)
    val address: String,

    @Column(name = "recipient", nullable = false)
    val recipient: String,

    @Column(name = "order_request", nullable = true)
    val orderRequest: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member

) : BaseTimeEntity() {

    @Column(name = "order_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Int? = null

}