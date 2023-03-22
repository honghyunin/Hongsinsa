package commerce.hongsinsa.entity.coupon

import commerce.hongsinsa.entity.brand.Brand
import javax.persistence.*

@Entity
class Coupon(

    @Column(name = "name", nullable = false)
    val name: String? = null,

    @Column(name = "discount", nullable = false)
    val discount: Byte,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = true)
    var brand: Brand? = null
) {

    @Column(name = "id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @Column(name = "is_delete", nullable = false)
    var isDelete: Boolean = false
}