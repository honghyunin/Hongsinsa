package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.coupon.SaveCouponDto
import commerce.hosinsa.domain.repository.BrandRepository
import commerce.hosinsa.domain.repository.CouponRepository
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import commerce.hosinsa.global.extension.settingExpiredAt
import commerce.hosinsa.global.extension.toCoupon
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val brandRepository: BrandRepository,
    private val couponRepository: CouponRepository
) {
    fun saveCoupon(saveCouponDto: SaveCouponDto) {
        saveCouponDto.also {
            it.settingExpiredAt()

            if (saveCouponDto.brandName == null)
                couponRepository.save(saveCouponDto.toCoupon(null))
            else
                couponRepository.save(saveCouponDto.toCoupon(findBrandByName(saveCouponDto.brandName)))
        }
    }

    private fun findBrandByName(brandName: String) = brandRepository.findByNameAndIsDeleteFalse(brandName)
        ?: throw CustomException(ErrorCode.BRAND_NOT_FOUND)
}

