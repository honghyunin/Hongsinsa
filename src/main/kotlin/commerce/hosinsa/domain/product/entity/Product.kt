package commerce.hosinsa.domain.product.entity

import commerce.hosinsa.domain.baseTime.entity.BaseTimeEntity
import commerce.hosinsa.domain.brand.entity.Brand
import javax.persistence.*

@Entity
class Product(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "price", nullable = false)
    val price: Int,

    @Column(name = "category", nullable = false)
    val category: String,

    @Column(name = "gender", nullable = false)
    val gender: Char,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    val brand: Brand

): BaseTimeEntity() {

    @Column(name = "product_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: Int? = null
}