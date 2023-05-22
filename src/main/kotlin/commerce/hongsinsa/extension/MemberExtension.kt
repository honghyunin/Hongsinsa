package commerce.hongsinsa.extension


import commerce.hongsinsa.dto.member.ChangePasswordDto
import commerce.hongsinsa.dto.member.SignUpDto
import commerce.hongsinsa.dto.member.PatchProfileDto
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.member.Role
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode

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

fun Member.patchProfile(patchProfileDto: PatchProfileDto): Member {
    this.pw = patchProfileDto.password
    this.name = patchProfileDto.name
    this.nickname = patchProfileDto.nickname
    this.email = patchProfileDto.email
    this.weight = patchProfileDto.weight
    this.height = patchProfileDto.height
    this.phoneNumber = patchProfileDto.phoneNumber

    return this
}

fun Member.changePassword(changePassword: String): Member {
    this.pw = changePassword

    return this
}

fun ChangePasswordDto.throwIfIsNotMatchesNewPasswordAndReNewPassword(): ChangePasswordDto =
    if (this.newPassword != this.reNewPassword) throw CustomException(ErrorCode.CHANGE_PASSWORD_NOT_MATCH)
    else this
