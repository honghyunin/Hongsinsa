package commerce.hongsinsa.domain.dto.member

class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val id: String,
    val idx: Int
)