package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.brand.AvailableBrandDto
import commerce.hosinsa.domain.dto.brand.BrandUpdateDto
import commerce.hosinsa.domain.repository.BrandRepository
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import commerce.hosinsa.global.extension.setIsAudit
import commerce.hosinsa.global.extension.toBrand
import commerce.hosinsa.global.extension.updateBrand
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {
    fun brandAvailable(availableBrandDto: AvailableBrandDto) {

        if (existsByName(availableBrandDto)) throw CustomException(ErrorCode.BRAND_ALREADY_EXISTS)

        brandRepository.save(availableBrandDto.toBrand())
    }

    fun auditAvailable(brandName: String) {
        val brand = brandRepository.findByNameAndIsDeleteFalse(brandName) ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

        brand.setIsAudit()
    }

    @Transactional
    fun brandUpdate(brandUpdateDto: BrandUpdateDto) {
        val brand = brandRepository.findByNameAndIsDeleteFalse(brandUpdateDto.name) ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

        brand.updateBrand(brandUpdateDto)
    }

    fun existsByName(availableBrandDto: AvailableBrandDto) = brandRepository.existsByNameAndIsDeleteFalse(availableBrandDto.name)

    fun findBrandByName(brandName: String) = brandRepository.findByNameAndIsDeleteFalse(brandName)
        ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

}