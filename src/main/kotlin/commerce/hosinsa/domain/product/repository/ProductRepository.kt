package commerce.hosinsa.domain.product.repository

import commerce.hosinsa.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {

    fun findByProductId(productId: Int): Product?
    fun findByName(name: String): Product?
    fun existsByName(name: String): Boolean
}