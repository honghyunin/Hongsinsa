package commerce.hongsinsa.domain.brand

import commerce.hongsinsa.domain.dto.brand.AvailableBrandDto
import commerce.hongsinsa.domain.dto.brand.UpdateBrandDto
import commerce.hongsinsa.domain.service.brand.BrandService
import commerce.hongsinsa.global.extension.toBrand
import io.mockk.mockk

const val NAME: String = "gowl"
const val EMAIL: String = "this.email"
const val HOMEPAGE_URL: String = "this.homepageUrl"
const val INTRODUCE: String = "this.introduce"
const val COMPANY: String = "this.company"
const val BUSINESS_NUMBER: String = "1111111"
const val MANAGER_NAME: String = "this.managerName"
const val BUSINESS_ADDRESS: String = "this.businessAddress"
const val PHONE_NUMBER: String = "this.phoneNumber"
const val IS_AUDIT: Boolean = false

val brandAvailableDto = AvailableBrandDto(
    name = NAME,
    email = EMAIL,
    homepageUrl = HOMEPAGE_URL,
    introduce = INTRODUCE,
    company = COMPANY,
    businessNumber = BUSINESS_NUMBER,
    managerName = MANAGER_NAME,
    businessAddress = BUSINESS_ADDRESS,
    phoneNumber = PHONE_NUMBER,
    isAudit = IS_AUDIT
)

val updateBrandDto = UpdateBrandDto(
    name = NAME,
    email = EMAIL,
    homepageUrl = HOMEPAGE_URL,
    introduce = INTRODUCE,
    company = COMPANY,
    businessNumber = BUSINESS_NUMBER,
    managerName = MANAGER_NAME,
    businessAddress = BUSINESS_ADDRESS,
    phoneNumber = PHONE_NUMBER,
)

val BRAND = brandAvailableDto.toBrand()

val brandService = mockk<BrandService>()