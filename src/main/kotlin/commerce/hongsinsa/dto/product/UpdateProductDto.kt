package commerce.hongsinsa.dto.product

import io.swagger.v3.oas.annotations.media.Schema

class UpdateProductDto(
    val productIdx: Int,
    val name: String,
    val price: Int,
    val category: String,
    @Schema(example = "M")
    val gender: Char,
    val stock: Int
)
