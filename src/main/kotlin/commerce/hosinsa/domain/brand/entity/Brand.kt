package commerce.hosinsa.domain.brand.entity

import commerce.hosinsa.domain.product.entity.Product
import javax.persistence.*

@Entity
class Brand(
    @Column(name = "name", nullable = false, unique = true)
    var name: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "homepage_url", nullable = false)
    var homepageUrl: String,

    @Column(name = "introduce", nullable = false)
    var introduce: String,

    @Column(name = "company", nullable = false)
    var company: String,

    @Column(name = "business_number", nullable = false)
    var businessNumber: String,

    @Column(name = "business_address", nullable = false)
    var businessAddress: String,

    @Column(name = "manager_name", nullable = false)
    var managerName: String,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "is_audit", nullable = false)
    var isAudit: Boolean = false,

    @Column(name = "is_delete", nullable = false)
    val isDelete: Boolean = false,
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