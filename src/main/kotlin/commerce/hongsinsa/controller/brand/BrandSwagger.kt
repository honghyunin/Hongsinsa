package commerce.hongsinsa.controller.brand

import commerce.hongsinsa.dto.brand.AvailableBrandDto
import commerce.hongsinsa.dto.brand.UpdateBrandDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "브랜드관리", description = "브랜드 관리 API")
interface BrandSwagger {

    @Operation(summary = "브랜드 입점", description = "브랜드 입점")
    fun brandAvailable(availableBrandDto: AvailableBrandDto): ResponseEntity<Any>

    @Operation(summary = "브랜드 입점 심사", description = "실제로 운영중인 브랜드일 경우 브랜드 입점을 승인")
    fun auditAvailable(brandName: String): ResponseEntity<Any>

    @Operation(summary = "브랜드 정보 변경", description = "브랜드의 상세 정보를 변경")
    fun brandUpdate(updateBrandDto: UpdateBrandDto): ResponseEntity<Any>
}