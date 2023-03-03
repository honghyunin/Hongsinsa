package commerce.hosinsa.domain.repository

import commerce.hosinsa.domain.dto.cart.CartResponse

interface CartCustomRepository {
    fun findProductsByMemberIdx(memberIdx: Int): MutableList<CartResponse>
}