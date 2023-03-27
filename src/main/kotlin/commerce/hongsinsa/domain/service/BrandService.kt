package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.dto.brand.AvailableBrandDto
import commerce.hongsinsa.domain.dto.brand.UpdateBrandDto
import commerce.hongsinsa.domain.repository.brand.BrandRepository
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode
import commerce.hongsinsa.global.extension.setIsAudit
import commerce.hongsinsa.global.extension.toBrand
import commerce.hongsinsa.global.extension.updateBrand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


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

    fun brandUpdate(updateBrandDto: UpdateBrandDto) {
        val brand = brandRepository.findByNameAndIsDeleteFalse(updateBrandDto.name)
            ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

        brand.updateBrand(updateBrandDto)
    }

    fun existsByName(availableBrandDto: AvailableBrandDto) = brandRepository.existsByNameAndIsDeleteFalse(availableBrandDto.name)

    fun findBrandByName(brandName: String) = brandRepository.findByNameAndIsDeleteFalse(brandName)
        ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

}