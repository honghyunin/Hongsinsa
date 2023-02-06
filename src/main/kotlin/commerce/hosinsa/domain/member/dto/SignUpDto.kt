package commerce.hosinsa.domain.member.dto

import java.time.LocalDateTime

class SignUpDto(
    val email: String,
    val id: String,
    var pw: String,
    val name: String,
    val nickname: String,
    val weight: Short,
    val height: Short,
    val gender: Char,
    val age: Short,
    val phoneNumber: String,
    val address: String,
    val birthday: LocalDateTime = LocalDateTime.now()
)