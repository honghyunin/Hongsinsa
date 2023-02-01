package commerce.hosinsa.global.config.utils

import commerce.hosinsa.domain.member.dto.SignUpDto
import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.member.entity.Role

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
