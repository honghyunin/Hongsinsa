package commerce.hosinsa.entity.product

import commerce.hosinsa.entity.BaseTimeEntity
import commerce.hosinsa.entity.brand.Brand
import javax.persistence.*

@Entity
class Product(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "price", nullable = false)
    var price: Int,

    @Column(name = "category", nullable = false)
    var category: String,

    @Column(name = "gender", nullable = false)
    var gender: Char,

    @Column(name = "stock", nullable = false)
    var stock: Int,

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    val brand: Brand

) : BaseTimeEntity() {

    @Column(name = "product_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: Int? = null

    @Column(name = "is_soludout")
    var isSoldOut: Boolean = false

}