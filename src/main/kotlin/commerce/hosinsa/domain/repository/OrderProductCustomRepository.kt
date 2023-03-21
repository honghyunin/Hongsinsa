package commerce.hosinsa.domain.repository

import commerce.hosinsa.domain.dto.order.GetOrderResponse
import commerce.hosinsa.entity.product.Product

interface OrderProductCustomRepository {
    fun findGetOrderResponsesByMemberIdx(memberIdx: Int): MutableList<GetOrderResponse>
    fun findProductByProductIdxAndIsDeleteFalse(productIdx: Int): Product?
}