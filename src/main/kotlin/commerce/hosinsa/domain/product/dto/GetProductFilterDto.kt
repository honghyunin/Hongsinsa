package commerce.hosinsa.domain.product.dto

import commerce.hosinsa.domain.product.enumerated.Price

class GetProductFilterDto(
    var name: String?,
    var price: Price?,
    var category: String?,
    var gender: Char?,
    var brandName: String?
)