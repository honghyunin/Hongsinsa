package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.product.Product
import commerce.hosinsa.entity.product.ProductOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductOptionRepository: JpaRepository<ProductOption, Product> {
    fun findByProductIdx(productIdx: Int): MutableList<ProductOption>
}
