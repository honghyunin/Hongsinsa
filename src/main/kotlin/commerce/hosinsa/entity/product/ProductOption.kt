package commerce.hosinsa.entity.product

import javax.persistence.*

@Table(name = "product_option")
@Entity
class ProductOption(
    @Id
    @Column(name = "product_idx", nullable = false)
    val productIdx: Int,

    @Column(name = "color", nullable = false)
    val color: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "product_size", nullable = false)
    val size: ProductSize
)
