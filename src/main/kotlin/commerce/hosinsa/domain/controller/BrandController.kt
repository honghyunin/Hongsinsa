package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.brand.AvailableBrandDto
import commerce.hosinsa.domain.dto.brand.BrandUpdateDto
import commerce.hosinsa.domain.service.BrandService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brand")
class BrandController(private val brandService: BrandService) {

    @PostMapping("/available")
    fun brandAvailable(@RequestBody availableBrandDto: AvailableBrandDto): Unit = brandService.brandAvailable(availableBrandDto)

    @PutMapping("/audit/available/{brandName}")
    fun auditAvailable(@PathVariable brandName: String): Unit = brandService.auditAvailable(brandName)

    @PutMapping("/update")
    fun brandUpdate(@RequestBody brandUpdateDto: BrandUpdateDto): Unit = brandService.brandUpdate(brandUpdateDto)
}