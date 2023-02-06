package commerce.hosinsa.domain.brand.service

import commerce.hosinsa.domain.brand.dto.AvailableBrandDto
import commerce.hosinsa.domain.brand.dto.BrandUpdateDto

interface BrandService {

    fun brandAvailable(availableBrandDto: AvailableBrandDto)
    fun auditAvailable(brandName: String)
    fun brandUpdate(brandUpdateDto: BrandUpdateDto)
}