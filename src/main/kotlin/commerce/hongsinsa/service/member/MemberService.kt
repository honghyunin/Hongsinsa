package commerce.hongsinsa.service.member

import commerce.hongsinsa.dto.member.ChangePasswordDto
import commerce.hongsinsa.dto.member.PatchProfileDto
import commerce.hongsinsa.repository.member.MemberRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import commerce.hongsinsa.extension.changePassword
import commerce.hongsinsa.extension.throwIfIsNotMatchesNewPasswordAndReNewPassword
import commerce.hongsinsa.extension.patchProfile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    @Transactional(rollbackFor = [Exception::class])
    fun save(member: Member) {
        memberRepository.save(member)
    }

    @Transactional(readOnly = true)
    fun throwExceptionIfIsExistByMemberId(id: String){
        if(memberRepository.existsByIdAndIsDeleteFalse(id))
            throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun throwExceptionIfIsNotExistByMemberId(id: String){
        if(!memberRepository.existsByIdAndIsDeleteFalse(id))
            throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun patchProfile(member: Member, patchProfileDto: PatchProfileDto) {
        member.patchProfile(patchProfileDto)

        save(member)
    }

    fun findByMemberEmail(email: String): Member = memberRepository.findByEmailAndIsDeleteFalse(email)
        ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

    fun findByMemberIdx(idx: Int): Member = memberRepository.findByIdx(idx)
        ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

    @Transactional(rollbackFor = [Exception::class])
    fun changePassword(member: Member, reNewPassword: String) {
        member.changePassword(reNewPassword)
    }

    @Transactional(readOnly = true)
    fun getMember(memberIdx: Int) = memberRepository.findByIdxAndIsDeleteFalse(memberIdx)
        ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

    @Transactional(readOnly = true)
    fun findByIdAndIsDeleteFalse(id: String) = memberRepository.findByIdAndIsDeleteFalse(id)
        ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

    @Transactional(readOnly = true)
    fun existsByIdx(memberIdx: Int) = memberRepository.existsById(memberIdx)
}