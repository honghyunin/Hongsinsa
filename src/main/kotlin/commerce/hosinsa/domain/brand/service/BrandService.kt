package commerce.hosinsa.domain.brand.service

import commerce.hosinsa.domain.brand.dto.BrandAvailableDto
import commerce.hosinsa.domain.brand.dto.BrandUpdateDto

interface BrandService {

    fun brandAvailable(brandAvailableDto: BrandAvailableDto)
    fun auditAvailable(brandName: String)
    fun brandUpdate(brandUpdateDto: BrandUpdateDto)
}