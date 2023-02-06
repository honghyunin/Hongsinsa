package commerce.hosinsa.domain.member.service

import commerce.hosinsa.domain.member.dto.*

interface MemberService {

    fun signUp(signUpDto: SignUpDto)
    fun signIn(signInDto: SignInDto): TokenResponse
    fun updateProfile(updateProfileDto: UpdateProfileDto)
    fun changePassword(changePasswordDto: ChangePasswordDto): String
}