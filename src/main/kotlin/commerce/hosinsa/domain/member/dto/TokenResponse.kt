package commerce.hosinsa.domain.member.dto

class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val id: String
)