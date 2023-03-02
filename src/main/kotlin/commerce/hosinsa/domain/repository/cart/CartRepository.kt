package commerce.hosinsa.domain.repository.cart

import commerce.hosinsa.entity.cart.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int> {
    fun findByProductIdx(productIdx: Int): Cart?
}
