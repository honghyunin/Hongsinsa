package commerce.hongsinsa.controller.cart

import commerce.hongsinsa.dto.cart.AddProductDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "장바구니관리", description = "장바구니 관리 API")
interface CartSwagger {

    @Operation(summary = "장바구니 상품 추가", description = "장바구니 상품 추가 API")
    fun addProduct(addProductDto: AddProductDto): ResponseEntity<Any>

    @Operation(summary = "장바구니 조회", description = "장바구니 조회 API")
    fun getCart(memberIdx: Int): ResponseEntity<Any>

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 담긴 상품을 삭제")
    fun deleteCartProduct(productIdx: Int): ResponseEntity<Any>
}