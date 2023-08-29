package commerce.hongsinsa.controller.request

import commerce.hongsinsa.dto.request.RequestDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "주문관리", description = "주문관리 API")
interface RequestSwagger {

    @Operation(summary = "상품 주문", description = "상품 주문 API")
    fun request(requestDto: RequestDto): ResponseEntity<Any>

    @Operation(summary = "내 주문 조회", description = "내 주문 조회 API")
    fun getRequest(memberIdx: Int): ResponseEntity<Any>

    @Operation(summary = "주문 취소", description = "주문 취소 API")
    fun cancelRequest(requestIdx: Int): ResponseEntity<Any>
}