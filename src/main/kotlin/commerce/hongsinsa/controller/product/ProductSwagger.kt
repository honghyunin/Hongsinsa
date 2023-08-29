package commerce.hongsinsa.controller.product

import commerce.hongsinsa.dto.product.GetProductFilterDto
import commerce.hongsinsa.dto.product.ProductOptionDto
import commerce.hongsinsa.dto.product.RegistrationProductDto
import commerce.hongsinsa.dto.product.UpdateProductDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

@Tag(name = "상품관리", description = "상품관리 API")
interface ProductSwagger {

    @Operation(summary = "상품 등록", description = "판매자가 상품을 등록하는 API")
    fun registrationProduct(registrationProductDto: RegistrationProductDto): ResponseEntity<Any>

    @Operation(summary = "상품 옵션 추가", description = "상품에 대한 상세 정보 등록")
    fun addProductOptions(productIdx: Int, newOptions: MutableList<ProductOptionDto>): ResponseEntity<Any>

    @Operation(summary = "상품 정보 변경", description = "상품에 대한 정보를 변경")
    fun updateProduct(updateProduct: UpdateProductDto): ResponseEntity<Any>

    @Operation(summary = "상품 옵션 변경", description = "상품 옵션을 변경")
    fun updateProductOption(productIdx: Int, newOptions: MutableList<ProductOptionDto>): ResponseEntity<Any>

    @Operation(summary = "상품 상세 조회", description = "상품에 대한 상세 정보를 조회")
    fun getProduct(productIdx: Int): ResponseEntity<Any>

    @Operation(summary = "상품 전체 조회", description = "상품 전체 조회 API")
    fun getProducts(getProductFilterDto: GetProductFilterDto, pageable: Pageable): ResponseEntity<Any>

    @Operation(summary = "상품 옵션 조회", description = "상품 옵션 상세 조회 API")
    fun getProductOption(productIdx: Int): ResponseEntity<Any>

    @Operation(summary = "상품 삭제", description = "상품 삭제 API")
    fun deleteProduct(productIdx: Int): ResponseEntity<Any>

    @Operation(summary = "상품 매진 처리 ", description = "stock이 0인 상품을 매진 처리하는 API")
    fun updateIsSoldOut(productIdx: Int): ResponseEntity<Any>

}