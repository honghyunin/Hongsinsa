package commerce.hongsinsa.global.extension

import commerce.hongsinsa.domain.dto.brand.AvailableBrandDto
import commerce.hongsinsa.domain.dto.brand.BrandUpdateDto
import commerce.hongsinsa.entity.brand.Brand

fun AvailableBrandDto.toBrand(): Brand = Brand(
    name = this.name,
    email = this.email,
    homepageUrl = this.homepageUrl,
    introduce = this.introduce,
    company = this.company,
    businessNumber = this.businessNumber,
    managerName = this.managerName,
    businessAddress = this.businessAddress,
    phoneNumber = this.phoneNumber,
    isAudit = this.isAudit,
)

fun Brand.setIsAudit() {
    this.isAudit = true
}

fun Brand.updateBrand(brandUpdateDto: BrandUpdateDto) {
    name = brandUpdateDto.name
    email = brandUpdateDto.email
    homepageUrl = brandUpdateDto.homepageUrl
    introduce = brandUpdateDto.introduce
    company = brandUpdateDto.company
    businessNumber = brandUpdateDto.businessNumber
    managerName = brandUpdateDto.managerName
    businessAddress = brandUpdateDto.businessAddress
    phoneNumber = brandUpdateDto.phoneNumber
}
