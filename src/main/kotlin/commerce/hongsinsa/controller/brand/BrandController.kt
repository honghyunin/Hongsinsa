package commerce.hongsinsa.controller.brand

import commerce.hongsinsa.dto.brand.AvailableBrandDto
import commerce.hongsinsa.dto.brand.UpdateBrandDto
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import commerce.hongsinsa.service.brand.BrandService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/brands")
class BrandController(private val brandService: BrandService) : BrandSwagger {

    @PostMapping("/available")
    override fun brandAvailable(@RequestBody availableBrandDto: AvailableBrandDto): ResponseEntity<Any> {
        ifIsThrowExistsByName(availableBrandDto.name)
        brandService.brandAvailable(availableBrandDto)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    private fun ifIsThrowExistsByName(name: String) {
        if (brandService.existsByName(name))
            throw CustomException(ErrorCode.BRAND_ALREADY_EXISTS)
    }

    @PutMapping("/audit/available/{brandName}")
    override fun auditAvailable(@PathVariable brandName: String): ResponseEntity<Any> {
        val brand = brandService.findBrandByName(brandName)
        brandService.auditAvailable(brand)

        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/update")
    override fun brandUpdate(@RequestBody updateBrandDto: UpdateBrandDto): ResponseEntity<Any> {
        val brand = brandService.findBrandByName(updateBrandDto.name)
        brandService.brandUpdate(brand, updateBrandDto)

        return ResponseEntity<Any>(HttpStatus.OK)
    }
}