package commerce.hosinsa.domain.dto.member

class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val id: String,
    val memberId: Int
)