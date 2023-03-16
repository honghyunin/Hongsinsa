package commerce.hosinsa.domain.dto.product

import com.querydsl.core.annotations.QueryProjection
import commerce.hosinsa.entity.product.ProductOption

class ProductResponse @QueryProjection constructor(
    val productId: Int,
    val name: String,
    val price: Int,
    val category: String,
    val gender: Char,
    val brand: String,
) {
    var options: MutableList<ProductOption> = mutableListOf()
}