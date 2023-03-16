package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.member.*
import commerce.hosinsa.domain.repository.MemberRepository
import commerce.hosinsa.entity.member.Role
import commerce.hosinsa.global.config.utils.CurrentUserUtil
import commerce.hosinsa.global.config.utils.TokenUtils
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import commerce.hosinsa.global.extension.changePassword
import commerce.hosinsa.global.extension.matchesPassword
import commerce.hosinsa.global.extension.toMember
import commerce.hosinsa.global.extension.updateProfile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenUtils: TokenUtils,
    private val currentUserUtil: CurrentUserUtil
) {
    fun signUp(signUpDto: SignUpDto) {

        if (memberRepository.existsByIdAndIsDeleteFalse(signUpDto.id))
            throw CustomException(ErrorCode.MEMBER_ALREADY_EXISTS)

        signUpDto.apply { password = passwordEncoder.encode(this.password) }
            .let { memberRepository.save(it.toMember()) }
    }

    fun signIn(signInDto: SignInDto): TokenResponse {
        signInDto.let {
            val findMember = memberRepository.findByIdAndIsDeleteFalse(it.id) ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

            if (notMatchesPassword(signInDto.password, findMember.pw))
                throw CustomException(ErrorCode.PASSWORD_NOT_MATCH)

            return TokenResponse(
                accessToken = tokenUtils.createAccessToken(findMember.id, getRoleMember(findMember.roles)),
                refreshToken = tokenUtils.createRefreshToken(findMember.id, getRoleMember(findMember.roles)),
                findMember.id,
                findMember.idx!!
            )
        }
    }

    @Transactional
    fun updateProfile(updateProfileDto: UpdateProfileDto) {

        val member = updateProfileDto.apply { password = passwordEncoder.encode(password) }
            .let { memberRepository.findByEmailAndIsDeleteFalse(updateProfileDto.email) }
            ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

        member.updateProfile(updateProfileDto)
    }

    @Transactional
    fun changePassword(changePasswordDto: ChangePasswordDto): String {
        val currentUser = currentUserUtil.currentUser
            ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

        if (notMatchesPassword(changePasswordDto.currentPassword, currentUser.pw))
            throw CustomException(ErrorCode.PASSWORD_NOT_MATCH)

        return changePasswordDto.matchesPassword()
            .also { currentUser.changePassword(passwordEncoder.encode(it.newPassword)) }.newPassword
    }

    private fun notMatchesPassword(rawPassword: String, encodedPassword: String): Boolean =
        !passwordEncoder.matches(rawPassword, encodedPassword)

    private fun getRoleMember(roles: MutableList<Role>): MutableList<Role> =
        roles.filter { it != Role.MEMBER }.toMutableList().also { it.add(Role.MEMBER) }
}