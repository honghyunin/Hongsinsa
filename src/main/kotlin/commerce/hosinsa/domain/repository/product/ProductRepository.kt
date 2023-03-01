package commerce.hosinsa.domain.repository.product

import commerce.hosinsa.entity.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {

    fun findByProductId(productId: Int): Product?
    fun findByName(name: String): Product?
    fun existsByName(name: String): Boolean
}