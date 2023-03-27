package commerce.hongsinsa.domain.repository.order

import commerce.hongsinsa.domain.dto.order.GetOrderDto

interface OrderProductCustomRepository {
    fun findGetOrderResponsesByMemberIdx(memberIdx: Int): MutableList<GetOrderDto>
}