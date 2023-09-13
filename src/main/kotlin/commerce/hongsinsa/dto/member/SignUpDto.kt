package commerce.hongsinsa.dto.member

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

class SignUpDto(
    val email: String,
    val id: String,
    var password: String,
    val name: String,
    val nickname: String,
    val weight: Short,
    val height: Short,
    @Schema(example = "M")
    val gender: Char,
    val age: Short,
    val phoneNumber: String,
    val address: String,
    val birthday: LocalDate
)