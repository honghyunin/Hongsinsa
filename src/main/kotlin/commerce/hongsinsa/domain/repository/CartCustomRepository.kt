package commerce.hongsinsa.domain.repository

import commerce.hongsinsa.domain.dto.cart.CartResponse

interface CartCustomRepository {
    fun findProductsByMemberIdx(memberIdx: Int): MutableList<CartResponse>
}