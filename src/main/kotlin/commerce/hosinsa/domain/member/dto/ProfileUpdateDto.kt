package commerce.hosinsa.domain.member.dto

class ProfileUpdateDto(
    var pw: String,
    val name: String,
    val nickname: String,
    val email: String,
    val weight: Short,
    val height: Short,
    val phoneNumber: String,
)
