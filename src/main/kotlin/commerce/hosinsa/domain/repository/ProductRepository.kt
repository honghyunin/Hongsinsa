package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Int> {
    fun findByIdxAndIsDeleteFalse(productIdx: Int): Product?
}