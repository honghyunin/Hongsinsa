package commerce.hongsinsa.domain.repository

import commerce.hongsinsa.entity.cart.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int> {
    fun findByProductIdxAndMemberIdxAndIsDeleteFalse(productIdx: Int, memberIdx: Int): Cart?
}
