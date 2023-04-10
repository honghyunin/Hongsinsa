package commerce.hongsinsa.service.brand

import commerce.hongsinsa.dto.brand.AvailableBrandDto
import commerce.hongsinsa.dto.brand.UpdateBrandDto
import commerce.hongsinsa.repository.brand.BrandRepository
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import commerce.hongsinsa.extension.setIsAudit
import commerce.hongsinsa.extension.toBrand
import commerce.hongsinsa.extension.updateBrand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BrandService(
    private val brandRepository: BrandRepository
) {
    @Transactional(rollbackFor = [Exception::class])
    fun brandAvailable(availableBrandDto: AvailableBrandDto) {

        if (existsByName(availableBrandDto)) throw CustomException(ErrorCode.BRAND_ALREADY_EXISTS)

        brandRepository.save(availableBrandDto.toBrand())
    }

    @Transactional(rollbackFor = [Exception::class])
    fun auditAvailable(brandName: String) {
        val brand = brandRepository.findByNameAndIsDeleteFalse(brandName)
            ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

        brand.setIsAudit()
    }

    @Transactional(rollbackFor = [Exception::class])
    fun brandUpdate(updateBrandDto: UpdateBrandDto) {
        val brand = brandRepository.findByNameAndIsDeleteFalse(updateBrandDto.name)
            ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

        brand.updateBrand(updateBrandDto)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun existsByName(availableBrandDto: AvailableBrandDto) =
        brandRepository.existsByNameAndIsDeleteFalse(availableBrandDto.name)

    @Transactional(readOnly = true)
    fun findBrandByName(brandName: String) = brandRepository.findByNameAndIsDeleteFalse(brandName)
        ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

}