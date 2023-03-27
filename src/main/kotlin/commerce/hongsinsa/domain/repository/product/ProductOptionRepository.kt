package commerce.hongsinsa.domain.repository.product

import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.entity.product.ProductOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductOptionRepository: JpaRepository<ProductOption, Product> {
    fun findByProductIdx(productIdx: Int): MutableList<ProductOption>
}
