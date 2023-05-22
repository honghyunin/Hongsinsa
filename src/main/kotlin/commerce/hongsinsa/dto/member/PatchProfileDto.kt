package commerce.hongsinsa.dto.member

class PatchProfileDto(
    var password: String,
    val name: String,
    val nickname: String,
    val email: String,
    val weight: Short,
    val height: Short,
    val phoneNumber: String,
)
