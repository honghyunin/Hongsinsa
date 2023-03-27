package commerce.hongsinsa.domain.dto.member

class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val id: String,
    val idx: Int
)