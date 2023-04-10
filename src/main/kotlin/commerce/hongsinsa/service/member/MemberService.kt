package commerce.hongsinsa.service.member

import commerce.hongsinsa.dto.member.ChangePasswordDto
import commerce.hongsinsa.dto.member.SignInDto
import commerce.hongsinsa.dto.member.SignUpDto
import commerce.hongsinsa.dto.member.UpdateProfileDto
import commerce.hongsinsa.repository.member.MemberRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.member.Role
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import commerce.hongsinsa.exception.ErrorCode.MEMBER_NOT_FOUND
import commerce.hongsinsa.extension.changePassword
import commerce.hongsinsa.extension.matchesPassword
import commerce.hongsinsa.extension.toMember
import commerce.hongsinsa.extension.updateProfile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    @Transactional(rollbackFor = [Exception::class])
    fun signUp(signUpDto: SignUpDto) {
        if (memberRepository.existsByIdAndIsDeleteFalse(signUpDto.id))
            throw CustomException(ErrorCode.MEMBER_ALREADY_EXISTS)

        signUpDto.apply { password = passwordEncoder.encode(this.password) }
            .let { memberRepository.save(it.toMember()) }
    }

    @Transactional(readOnly = true)
    fun signIn(signInDto: SignInDto): Member {
        signInDto.let {
            val findMember = findByIdAndIsDeleteFalse(it.id)

            if (notMatchesPassword(signInDto.password, findMember.pw))
                throw CustomException(ErrorCode.PASSWORD_NOT_MATCH)

            return findMember
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun updateProfile(updateProfileDto: UpdateProfileDto) {

        val member = updateProfileDto.apply { password = passwordEncoder.encode(password) }
            .let { memberRepository.findByEmailAndIsDeleteFalse(updateProfileDto.email) }
            ?: throw CustomException(MEMBER_NOT_FOUND)

        member.updateProfile(updateProfileDto)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun changePassword(member: Member, changePasswordDto: ChangePasswordDto): String {
        if (notMatchesPassword(changePasswordDto.currentPassword, member.pw))
            throw CustomException(ErrorCode.PASSWORD_NOT_MATCH)

        return changePasswordDto.matchesPassword()
            .also { member.changePassword(passwordEncoder.encode(it.newPassword)) }.newPassword
    }

    private fun notMatchesPassword(rawPassword: String, encodedPassword: String): Boolean =
        !passwordEncoder.matches(rawPassword, encodedPassword)

    fun existsByIdx(memberIdx: Int){
        if(!memberRepository.existsByIdxAndIsDeleteFalse(memberIdx))
            throw CustomException(MEMBER_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun getMember(memberIdx: Int) = memberRepository.findByIdxAndIsDeleteFalse(memberIdx)
        ?: throw CustomException(MEMBER_NOT_FOUND)

    @Transactional(readOnly = true)
    fun findByIdAndIsDeleteFalse(id: String) = memberRepository.findByIdAndIsDeleteFalse(id)
        ?: throw CustomException(MEMBER_NOT_FOUND)
}