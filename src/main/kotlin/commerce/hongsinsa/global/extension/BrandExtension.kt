package commerce.hongsinsa.global.extension

import commerce.hongsinsa.domain.dto.brand.AvailableBrandDto
import commerce.hongsinsa.domain.dto.brand.UpdateBrandDto
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

fun Brand.updateBrand(updateBrandDto: UpdateBrandDto) {
    name = updateBrandDto.name
    email = updateBrandDto.email
    homepageUrl = updateBrandDto.homepageUrl
    introduce = updateBrandDto.introduce
    company = updateBrandDto.company
    businessNumber = updateBrandDto.businessNumber
    managerName = updateBrandDto.managerName
    businessAddress = updateBrandDto.businessAddress
    phoneNumber = updateBrandDto.phoneNumber
}
