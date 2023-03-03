package commerce.hosinsa.domain.repository

import commerce.hosinsa.domain.dto.order.GetOrderResponse

interface OrderProductCustomRepository {
    fun findGetOrderResponsesByMemberIdx(memberIdx: Int): MutableList<GetOrderResponse>
}