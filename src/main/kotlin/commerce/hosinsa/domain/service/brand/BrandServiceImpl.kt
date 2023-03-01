package commerce.hosinsa.domain.service.brand

import commerce.hosinsa.domain.dto.brand.AvailableBrandDto
import commerce.hosinsa.domain.dto.brand.BrandUpdateDto
import commerce.hosinsa.domain.repository.brand.BrandRepository
import commerce.hosinsa.global.config.utils.setIsAudit
import commerce.hosinsa.global.config.utils.toBrand
import commerce.hosinsa.global.config.utils.updateBrand
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.BRAND_ALREADY_EXISTS
import commerce.hosinsa.global.exception.ErrorCode.BRAND_NOT_FOUND
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BrandServiceImpl(
    private val brandRepository: BrandRepository
) : BrandService {
    override fun brandAvailable(availableBrandDto: AvailableBrandDto) {

        if (existsByName(availableBrandDto))
            throw CustomException(BRAND_ALREADY_EXISTS)

        brandRepository.save(availableBrandDto.toBrand())
    }

    override fun auditAvailable(brandName: String) {
        val brand = brandRepository.findByName(brandName)
            ?: throw CustomException(BRAND_NOT_FOUND)

        brand.setIsAudit()
    }

    @Transactional
    override fun brandUpdate(brandUpdateDto: BrandUpdateDto) {
        val brand = brandRepository.findByName(brandUpdateDto.name)
            ?: throw CustomException(BRAND_NOT_FOUND)

        brand.updateBrand(brandUpdateDto)
    }

    fun existsByName(availableBrandDto: AvailableBrandDto) = brandRepository.existsByName(availableBrandDto.name)
}