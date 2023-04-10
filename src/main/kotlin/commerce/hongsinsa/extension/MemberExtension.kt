package commerce.hongsinsa.extension


import commerce.hongsinsa.dto.member.ChangePasswordDto
import commerce.hongsinsa.dto.member.SignUpDto
import commerce.hongsinsa.dto.member.UpdateProfileDto
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
