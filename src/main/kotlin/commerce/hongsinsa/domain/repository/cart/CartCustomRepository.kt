package commerce.hongsinsa.domain.repository.cart

import commerce.hongsinsa.domain.dto.cart.GetCartDto

interface CartCustomRepository {
    fun findProductsByMemberIdx(memberIdx: Int): MutableList<GetCartDto>
}