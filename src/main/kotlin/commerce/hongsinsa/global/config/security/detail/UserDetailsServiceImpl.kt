package commerce.hongsinsa.global.config.security.detail

import commerce.hongsinsa.domain.repository.MemberRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(private val memberRepository: MemberRepository) : UserDetailsService {

    override fun loadUserByUsername(id: String): Member =
        memberRepository.findByIdAndIsDeleteFalse(id) ?: throw CustomException(MEMBER_NOT_FOUND)
}