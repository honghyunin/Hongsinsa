package commerce.hosinsa.global.config.utils

import commerce.hosinsa.domain.repository.member.MemberRepository
import commerce.hosinsa.entity.member.Member
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


@Component
class CurrentUserUtil(private val memberRepository: MemberRepository) {

    val currentUser: Member?
        get() {
            var id: String
            val principal = SecurityContextHolder.getContext().authentication.principal
            id = if (principal is UserDetails) {
                principal.username
            } else {
                principal.toString()
            }
            println("=================$id=================한")
            return memberRepository.findById(id)
        }
}