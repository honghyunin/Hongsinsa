package commerce.hongsinsa.domain.dto.product

import commerce.hongsinsa.entity.product.Price

class GetProductFilterDto(
    var name: String?,
    var price: Price?,
    var category: String?,
    var gender: Char?,
    var brandName: String?
)