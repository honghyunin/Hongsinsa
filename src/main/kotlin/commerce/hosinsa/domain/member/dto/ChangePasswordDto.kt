package commerce.hosinsa.domain.member.dto

class ChangePasswordDto(
    val currentPassword: String,
    val newPassword: String,
    val reNewPassword: String
)