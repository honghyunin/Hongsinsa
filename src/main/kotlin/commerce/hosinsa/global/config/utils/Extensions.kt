package commerce.hosinsa.global.config.utils

import commerce.hosinsa.domain.brand.dto.AvailableBrandDto
import commerce.hosinsa.domain.brand.dto.BrandUpdateDto
import commerce.hosinsa.domain.brand.entity.Brand
import commerce.hosinsa.domain.member.dto.ChangePasswordDto
import commerce.hosinsa.domain.member.dto.SignUpDto
import commerce.hosinsa.domain.member.dto.UpdateProfileDto
import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.member.entity.Role
import commerce.hosinsa.domain.product.dto.ProductResponse
import commerce.hosinsa.domain.product.dto.RegistrationProductDto
import commerce.hosinsa.domain.product.dto.UpdateProductDto
import commerce.hosinsa.domain.product.entity.Product
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.CHANGE_PASSWORD_NOT_MATCH

fun SignUpDto.toMember() = Member(
    email = this.email,
    id = this.id,
    pw = this.password,
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

fun Member.updateProfile(updateProfileDto: UpdateProfileDto): Member {
    this.pw = updateProfileDto.password
    this.name = updateProfileDto.name
    this.nickname = updateProfileDto.nickname
    this.email = updateProfileDto.email
    this.weight = updateProfileDto.weight
    this.height = updateProfileDto.height
    this.phoneNumber = updateProfileDto.phoneNumber

    return this
}

fun Member.changePassword(changePassword: String): Member {
    this.pw = changePassword

    return this
}

fun ChangePasswordDto.matchesPassword(): ChangePasswordDto =
    if (this.newPassword != this.reNewPassword) throw CustomException(CHANGE_PASSWORD_NOT_MATCH)
    else this

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

fun RegistrationProductDto.toProduct(brand: Brand) = Product(
    name = this.name,
    price = this.price,
    category = this.category,
    gender = this.gender,
    stock = this.stock,
    brand = brand
)

fun Product.updateProduct(updateProductDto: UpdateProductDto) {
    this.name = updateProductDto.name
    this.price = updateProductDto.price
    this.category = updateProductDto.category
    this.gender = updateProductDto.gender
}

fun Product.soldOut() {
    this.isSoldOut = true
}

fun Product.toProductResponse() = ProductResponse(
    productId = productId!!,
    name = name,
    price = price,
    category = category,
    gender = gender,
    brand = brand.name
)