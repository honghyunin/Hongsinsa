package commerce.hosinsa.entity.order

import commerce.hosinsa.entity.BaseTimeEntity
import commerce.hosinsa.entity.product.Product
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
@Table(name = "order_product")
class OrderProduct(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product.idx", nullable = false)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order.idx", nullable = false)
    val order: Order,

    @Comment(value = "상품 수량")
    @Column(name = "count", nullable = false)
    val count: Byte
): BaseTimeEntity() {
    @Column(name = "idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val idx: Int? = null
}