package commerce.hosinsa.domain.brand.entity

import commerce.hosinsa.domain.product.entity.Product
import javax.persistence.*

@Entity
class Brand(
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "email", nullable = false)
    var email: String? = null,

    @Column(name = "homepage_url", nullable = false)
    var homepageUrl: String? = null,

    @Column(name = "introduce", nullable = false)
    var introduce: String? = null,

    @Column(name = "company", nullable = false)
    var company: String? = null,

    @Column(name = "business_number", nullable = false)
    val businessNumber: Int? = null,

    @Column(name = "business_address", nullable = false)
    var businessAddress: String? = null,

    @Column(name = "manager_name", nullable = false)
    var managerName: String? = null,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String? = null
) {

    @Column(name = "brand_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val brandId: Int? = null

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "brand"
    )
    val products: MutableList<Product> = mutableListOf()
}