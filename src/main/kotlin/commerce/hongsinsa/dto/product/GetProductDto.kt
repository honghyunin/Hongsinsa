package commerce.hongsinsa.dto.product

import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema

class GetProductDto @QueryProjection constructor(
    val productId: Int,
    val name: String,
    val price: Int,
    val category: String,
    @Schema(example = "M")
    val gender: Char,
    val brand: String?,
    var options: MutableList<ProductOptionResponse>
)