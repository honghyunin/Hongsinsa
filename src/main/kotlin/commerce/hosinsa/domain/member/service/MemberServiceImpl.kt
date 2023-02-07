package commerce.hosinsa.domain.member.service

import commerce.hosinsa.domain.member.dto.*
import commerce.hosinsa.domain.member.entity.Role
import commerce.hosinsa.domain.member.repository.MemberRepository
import commerce.hosinsa.global.config.utils.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import javax.transaction.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenUtils: TokenUtils,
    private val currentUserUtil: CurrentUserUtil
) : MemberService {

    override fun signUp(signUpDto: SignUpDto) {

        if (memberRepository.existsById(signUpDto.id))
            throw CustomException(USER_ALREADY_EXISTS)

        signUpDto.apply { password = passwordEncoder.encode(this.password) }
            .let { memberRepository.save(it.toMember()) }
    }

    override fun signIn(signInDto: SignInDto): TokenResponse {
        signInDto.let {
            val findMember = memberRepository.findById(it.id) ?: throw CustomException(USER_NOT_FOUND)

            if (notMatchesPassword(signInDto.password, findMember.pw))
                throw CustomException(PASSWORD_NOT_MATCH)

            return TokenResponse(
                accessToken = tokenUtils.createAccessToken(findMember.id, getRoleMember(findMember.roles)),
                refreshToken = tokenUtils.createRefreshToken(findMember.id, getRoleMember(findMember.roles)),
                findMember.id
            )
        }
    }

    @Transactional
    override fun updateProfile(updateProfileDto: UpdateProfileDto) {

        val member = updateProfileDto.apply { password = passwordEncoder.encode(password) }
            .let { memberRepository.findByEmail(updateProfileDto.email) }
            ?: throw CustomException(USER_NOT_FOUND)

        member.updateProfile(updateProfileDto)
    }

    @Transactional
    override fun changePassword(changePasswordDto: ChangePasswordDto): String {
        val currentUser = currentUserUtil.currentUser
            ?: throw CustomException(USER_NOT_FOUND)

        if (notMatchesPassword(changePasswordDto.currentPassword, currentUser.pw))
            throw CustomException(PASSWORD_NOT_MATCH)

        return changePasswordDto.matchesPassword()
            .also { currentUser.changePassword(passwordEncoder.encode(it.newPassword)) }.newPassword
    }

    private fun notMatchesPassword(rawPassword: String, encodedPassword: String): Boolean =
        !passwordEncoder.matches(rawPassword, encodedPassword)

    private fun getRoleMember(roles: MutableList<Role>): MutableList<Role> =
        roles.filter { it != Role.MEMBER }.toMutableList().also { it.add(Role.MEMBER) }
}