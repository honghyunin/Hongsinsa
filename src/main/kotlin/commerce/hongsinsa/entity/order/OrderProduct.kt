package commerce.hongsinsa.entity.order

import commerce.hongsinsa.entity.BaseTimeEntity
import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.entity.product.ProductSize
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
@Table(name = "order_product")
class OrderProduct(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx", nullable = false)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_idx", nullable = false)
    val order: Order,

    @Comment(value = "상품 수량")
    @Column(name = "count", nullable = false)
    val count: Byte,

    @Column(name = "size")
    val size: ProductSize,

    @Column(name = "color")
    val color: String,
): BaseTimeEntity() {
    @Column(name = "idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val idx: Int? = null

    @Column(name = "is_delete", nullable = false)
    var isDelete: Boolean = false
}