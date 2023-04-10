package commerce.hongsinsa.repository.product

import commerce.hongsinsa.entity.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {
    fun findByIdxAndIsDeleteFalse(productIdx: Int): Product?
}