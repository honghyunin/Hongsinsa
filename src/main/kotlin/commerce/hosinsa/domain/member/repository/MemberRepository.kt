package commerce.hosinsa.domain.member.repository

import commerce.hosinsa.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Int> {

    fun findById(id: String): Member?

    fun existsById(id: String): Boolean
}