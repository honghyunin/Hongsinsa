package commerce.hongsinsa.domain.repository

import commerce.hongsinsa.domain.dto.order.GetOrderResponse
import commerce.hongsinsa.entity.product.Product

interface OrderProductCustomRepository {
    fun findGetOrderResponsesByMemberIdx(memberIdx: Int): MutableList<GetOrderResponse>
    fun findProductByProductIdxAndIsDeleteFalse(productIdx: Int): Product?
}