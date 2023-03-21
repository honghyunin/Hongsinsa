package commerce.hosinsa.entity.product

import javax.persistence.*

@Table(name = "product_option")
@Entity
class ProductOption(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    val product: Product,

    @Column(name = "color", nullable = false)
    val color: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "product_size", nullable = false)
    val size: ProductSize
) {
    @Id
    @Column(name = "idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Int? = null
}