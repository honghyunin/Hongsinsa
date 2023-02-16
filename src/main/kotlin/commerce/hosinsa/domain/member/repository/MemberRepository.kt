package commerce.hosinsa.domain.member.repository

import commerce.hosinsa.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Int> {

    fun findById(id: String): Member?

    fun findByEmail(email: String): Member?

    fun findByPw(pw: String): Member?

    fun existsById(id: String): Boolean
}