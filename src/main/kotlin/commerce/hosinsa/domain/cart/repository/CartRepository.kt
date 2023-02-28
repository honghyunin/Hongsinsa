package commerce.hosinsa.domain.cart.repository

import commerce.hosinsa.domain.cart.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int>
