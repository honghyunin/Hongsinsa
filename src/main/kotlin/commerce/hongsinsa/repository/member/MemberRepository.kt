package commerce.hongsinsa.repository.member

import commerce.hongsinsa.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Int> {
    fun findByIdx(idx: Int): Member?
    fun findByIdAndIsDeleteFalse(id: String): Member?
    fun findByEmailAndIsDeleteFalse(email: String): Member?
    fun existsByIdAndIsDeleteFalse(id: String): Boolean
    fun existsByIdxAndIsDeleteFalse(memberIdx: Int): Boolean
    fun findByIdxAndIsDeleteFalse(memberIdx: Int): Member?
}