package commerce.hosinsa.domain.member.service

import commerce.hosinsa.domain.member.dto.SignInDto
import commerce.hosinsa.domain.member.dto.SignUpDto
import commerce.hosinsa.domain.member.dto.TokenResponse
import commerce.hosinsa.domain.member.entity.Role
import commerce.hosinsa.domain.member.repository.MemberRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import commerce.hosinsa.global.config.utils.TokenUtils
import commerce.hosinsa.global.config.utils.toMember
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenUtils: TokenUtils,
): MemberService {

    override fun signUp(signUpDto: SignUpDto) {

        if (memberRepository.existsById(signUpDto.id))
            throw CustomException(USER_ALREADY_EXISTS)

        signUpDto.apply { pw = passwordEncoder.encode(this.pw) }
            .let { memberRepository.save(it.toMember()) }
    }

    override fun signIn(signInDto: SignInDto): TokenResponse {
        signInDto.let {
            val findMember = memberRepository.findById(it.id) ?: throw CustomException(USER_NOT_FOUND)

            if(passwordNotMatches(signInDto.pw, findMember.pw))
                throw CustomException(PASSWORD_NOT_MATCH)

            return TokenResponse(
                accessToken = tokenUtils.createAccessToken(findMember.id, getRoleMember()),
                refreshToken = tokenUtils.createRefreshToken(findMember.id, getRoleMember()),
                findMember.id
            )
        }
    }

    private fun passwordNotMatches(rawPassword: String, encodedPassword: String): Boolean =
        !passwordEncoder.matches(rawPassword, encodedPassword)

    private fun getRoleMember(): MutableList<Role> = mutableListOf(Role.MEMBER)
}