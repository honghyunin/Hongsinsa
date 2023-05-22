package commerce.hongsinsa.service.brand

import commerce.hongsinsa.dto.brand.AvailableBrandDto
import commerce.hongsinsa.dto.brand.UpdateBrandDto
import commerce.hongsinsa.entity.brand.Brand
import commerce.hongsinsa.entity.brand.QBrand.brand
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
        brandRepository.save(availableBrandDto.toBrand())
    }

    @Transactional(rollbackFor = [Exception::class])
    fun auditAvailable(brand: Brand) {
        brand.setIsAudit()
        brandRepository.save(brand)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun brandUpdate(brand: Brand, updateBrandDto: UpdateBrandDto) {
        brand.updateBrand(updateBrandDto)
        brandRepository.save(brand)
    }

    @Transactional(readOnly = true)
    fun existsByName(name: String) =
        brandRepository.existsByNameAndIsDeleteFalse(name)

    @Transactional(readOnly = true)
    fun findBrandByName(brandName: String) = brandRepository.findByNameAndIsDeleteFalse(brandName)
        ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)

}