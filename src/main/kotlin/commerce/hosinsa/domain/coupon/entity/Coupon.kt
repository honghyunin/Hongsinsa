package commerce.hosinsa.domain.coupon.entity

import commerce.hosinsa.domain.brand.entity.Brand
import javax.persistence.*

@Entity
class Coupon(

    @Column(name = "name", nullable = false)
    val name: String? = null,

    @Column(name = "discount", nullable = false)
    val discount: Short,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = true)
    var brand: Brand? = null
) {

    @Column(name = "coupon_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val couponId: Int? = null

    @Column(name = "is_delete", nullable = false)
    var isDelete: Boolean = false
}