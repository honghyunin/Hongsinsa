package commerce.hosinsa.domain.service.brand

import commerce.hosinsa.domain.dto.brand.AvailableBrandDto
import commerce.hosinsa.domain.dto.brand.BrandUpdateDto

interface BrandService {

    fun brandAvailable(availableBrandDto: AvailableBrandDto)
    fun auditAvailable(brandName: String)
    fun brandUpdate(brandUpdateDto: BrandUpdateDto)
}