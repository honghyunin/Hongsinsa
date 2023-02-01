package commerce.hosinsa.domain.member.service

import commerce.hosinsa.domain.member.dto.SignInDto
import commerce.hosinsa.domain.member.dto.SignUpDto
import commerce.hosinsa.domain.member.dto.TokenResponse

interface MemberService {

    fun signUp(signUpDto: SignUpDto)
    fun signIn(signInDto: SignInDto): TokenResponse
}