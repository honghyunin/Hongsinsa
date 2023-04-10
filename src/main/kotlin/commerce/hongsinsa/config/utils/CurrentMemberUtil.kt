package commerce.hongsinsa.config.utils

import commerce.hongsinsa.repository.member.MemberRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.FORBIDDEN
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


@Component
class CurrentMemberUtil(private val memberRepository: MemberRepository) {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    val currentMember: Member?
        get() {
            var id: String
            val principal = SecurityContextHolder.getContext().authentication.principal
            id = if (principal is UserDetails) {
                principal.username
            } else {
                principal.toString()
            }
            return memberRepository.findByIdAndIsDeleteFalse(id)
        }

    fun getCurrentMemberIfAuthenticated(): Member {
        log.error("================ Unauthorized Access ================")
        return currentMember ?: throw CustomException(FORBIDDEN)
    }
}