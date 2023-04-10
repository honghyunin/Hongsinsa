package commerce.hongsinsa.dto.member

import java.time.LocalDate

class SignUpDto(
    val email: String,
    val id: String,
    var password: String,
    val name: String,
    val nickname: String,
    val weight: Short,
    val height: Short,
    val gender: Char,
    val age: Short,
    val phoneNumber: String,
    val address: String,
    val birthday: LocalDate = LocalDate.now()
)