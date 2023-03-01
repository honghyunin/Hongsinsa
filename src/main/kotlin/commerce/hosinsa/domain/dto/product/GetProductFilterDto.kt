package commerce.hosinsa.domain.dto.product

import commerce.hosinsa.entity.product.Price

class GetProductFilterDto(
    var name: String?,
    var price: Price?,
    var category: String?,
    var gender: Char?,
    var brandName: String?
)