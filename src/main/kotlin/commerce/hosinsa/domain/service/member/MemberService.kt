package commerce.hosinsa.domain.service.member

import commerce.hosinsa.dto.member.*

interface MemberService {

    fun signUp(signUpDto: SignUpDto)
    fun signIn(signInDto: SignInDto): TokenResponse
    fun updateProfile(updateProfileDto: UpdateProfileDto)
    fun changePassword(changePasswordDto: ChangePasswordDto): String
}