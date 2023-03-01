package commerce.hosinsa.domain.dto.member

class ChangePasswordDto(
    val currentPassword: String,
    val newPassword: String,
    val reNewPassword: String
)