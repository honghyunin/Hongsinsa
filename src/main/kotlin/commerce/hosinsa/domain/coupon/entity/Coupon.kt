package commerce.hosinsa.domain.coupon.entity

import commerce.hosinsa.domain.brand.entity.Brand
import javax.persistence.*
import java.time.LocalDateTime

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

}