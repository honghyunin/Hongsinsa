package commerce.hongsinsa.controller.brand

import commerce.hongsinsa.dto.brand.AvailableBrandDto
import commerce.hongsinsa.dto.brand.UpdateBrandDto
import commerce.hongsinsa.service.brand.BrandService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/brand")
class BrandController(private val brandService: BrandService) {

    @PostMapping("/available")
    fun brandAvailable(@RequestBody availableBrandDto: AvailableBrandDto): Unit = brandService.brandAvailable(availableBrandDto)

    @PutMapping("/audit/available/{brandName}")
    fun auditAvailable(@PathVariable brandName: String): Unit = brandService.auditAvailable(brandName)

    @PutMapping("/update")
    fun brandUpdate(@RequestBody updateBrandDto: UpdateBrandDto): Unit = brandService.brandUpdate(updateBrandDto)
}