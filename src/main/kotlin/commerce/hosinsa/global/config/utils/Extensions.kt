package commerce.hosinsa.global.config.utils

import commerce.hosinsa.domain.brand.dto.BrandAvailableDto
import commerce.hosinsa.domain.brand.dto.BrandUpdateDto
import commerce.hosinsa.domain.brand.entity.Brand
import commerce.hosinsa.domain.member.dto.PWChangeDto
import commerce.hosinsa.domain.member.dto.ProfileUpdateDto
import commerce.hosinsa.domain.member.dto.SignUpDto
import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.member.entity.Role
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.CHANGE_PASSWORD_NOT_MATCH

fun SignUpDto.toMember() = Member(
    email = this.email,
    id = this.id,
    pw = this.pw,
    name = this.name,
    nickname = this.nickname,
    weight = this.weight,
    height = this.height,
    age = this.age,
    phoneNumber = this.phoneNumber,
    gender = this.gender,
    address = this.address,
    birthday = this.birthday,
    roles = mutableListOf(Role.MEMBER)
)

fun Member.profileUpdate(profileUpdateDto: ProfileUpdateDto): Member {
    this.pw = profileUpdateDto.pw
    this.name = profileUpdateDto.name
    this.nickname = profileUpdateDto.nickname
    this.email = profileUpdateDto.email
    this.weight = profileUpdateDto.weight
    this.height = profileUpdateDto.height
    this.phoneNumber = profileUpdateDto.phoneNumber

    return this
}

fun Member.pwChange(changePW: String): Member {
    this.pw = changePW

    return this
}

fun PWChangeDto.pwMatches(): PWChangeDto =
    if (this.newPW != this.reNewPW) throw CustomException(CHANGE_PASSWORD_NOT_MATCH)
    else this

fun BrandAvailableDto.toBrand(): Brand = Brand(
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

fun Brand.updateBrand(brandUpdateDto: BrandUpdateDto): Brand {
    name = brandUpdateDto.name
    email = brandUpdateDto.email
    homepageUrl = brandUpdateDto.homepageUrl
    introduce = brandUpdateDto.introduce
    company = brandUpdateDto.company
    businessNumber = brandUpdateDto.businessNumber
    managerName = brandUpdateDto.managerName
    businessAddress = brandUpdateDto.businessAddress
    phoneNumber = brandUpdateDto.phoneNumber

    return this
}