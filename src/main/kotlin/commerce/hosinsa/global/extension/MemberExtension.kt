package commerce.hosinsa.global.extension

import commerce.hosinsa.domain.dto.member.ChangePasswordDto
import commerce.hosinsa.domain.dto.member.SignUpDto
import commerce.hosinsa.domain.dto.member.UpdateProfileDto
import commerce.hosinsa.entity.member.Member
import commerce.hosinsa.entity.member.Role
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode

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
    if (this.newPassword != this.reNewPassword) throw CustomException(ErrorCode.CHANGE_PASSWORD_NOT_MATCH)
    else this
