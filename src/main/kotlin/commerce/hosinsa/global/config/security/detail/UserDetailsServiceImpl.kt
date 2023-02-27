package commerce.hosinsa.global.config.security.detail

import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.member.repository.MemberRepository
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(private val memberRepository: MemberRepository): UserDetailsService {

    override fun loadUserByUsername(id: String): Member =
        memberRepository.findById(id) ?: throw CustomException(MEMBER_NOT_FOUND)
}