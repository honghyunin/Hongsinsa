package commerce.hongsinsa.repository.request

import commerce.hongsinsa.entity.request.Request
import commerce.hongsinsa.entity.request.RequestProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestProductRepository: JpaRepository<RequestProduct, Int> {
    fun findAllByRequestAndIsDeleteFalse(request: Request): MutableList<RequestProduct>
}