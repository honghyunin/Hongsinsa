package commerce.hongsinsa.dto.product

import commerce.hongsinsa.entity.product.Price
import io.swagger.v3.oas.annotations.media.Schema

class GetProductFilterDto(
    var name: String?,
    var price: Price?,
    var category: String?,
    @Schema(example = "M")
    var gender: Char?,
    var brandName: String?
)