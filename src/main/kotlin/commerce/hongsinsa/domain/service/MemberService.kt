package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.dto.member.*
import commerce.hongsinsa.domain.repository.member.MemberRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.member.Role
import commerce.hongsinsa.global.config.utils.TokenUtils
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode
import commerce.hongsinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import commerce.hongsinsa.global.extension.changePassword
import commerce.hongsinsa.global.extension.matchesPassword
import commerce.hongsinsa.global.extension.toMember
import commerce.hongsinsa.global.extension.updateProfile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenUtils: TokenUtils,
) {
    fun signUp(signUpDto: SignUpDto) {
        if (memberRepository.existsByIdAndIsDeleteFalse(signUpDto.id))
            throw CustomException(ErrorCode.MEMBER_ALREADY_EXISTS)

        signUpDto.apply { password = passwordEncoder.encode(this.password) }
            .let { memberRepository.save(it.toMember()) }
    }

    fun signIn(signInDto: SignInDto): TokenResponse {
        signInDto.let {
            val findMember = findByIdAndIsDeleteFalse(it.id)

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
            ?: throw CustomException(MEMBER_NOT_FOUND)

        member.updateProfile(updateProfileDto)
    }

    @Transactional
    fun changePassword(member: Member, changePasswordDto: ChangePasswordDto): String {
        if (notMatchesPassword(changePasswordDto.currentPassword, member.pw))
            throw CustomException(ErrorCode.PASSWORD_NOT_MATCH)

        return changePasswordDto.matchesPassword()
            .also { member.changePassword(passwordEncoder.encode(it.newPassword)) }.newPassword
    }

    fun notMatchesPassword(rawPassword: String, encodedPassword: String): Boolean =
        !passwordEncoder.matches(rawPassword, encodedPassword)

    fun getRoleMember(roles: MutableList<Role>): MutableList<Role> =
        roles.filter { it != Role.MEMBER }.toMutableList().also { it.add(Role.MEMBER) }

    fun existsByIdx(memberIdx: Int){
        if(!memberRepository.existsByIdxAndIsDeleteFalse(memberIdx))
            throw CustomException(MEMBER_NOT_FOUND)
    }

    fun getMember(memberIdx: Int) = memberRepository.findByIdxAndIsDeleteFalse(memberIdx)
        ?: throw CustomException(MEMBER_NOT_FOUND)

    fun findByIdAndIsDeleteFalse(id: String) = memberRepository.findByIdAndIsDeleteFalse(id) ?: throw CustomException(MEMBER_NOT_FOUND)
}