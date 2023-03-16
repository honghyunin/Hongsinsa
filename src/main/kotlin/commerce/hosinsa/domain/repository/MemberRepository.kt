package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Int> {
    fun findByIdAndIsDeleteFalse(id: String): Member?
    fun findByEmailAndIsDeleteFalse(email: String): Member?
    fun existsByIdAndIsDeleteFalse(id: String): Boolean
    fun existsByIdxAndIsDeleteFalse(idx: Int): Boolean
}