package commerce.hosinsa.domain.repository.member

import commerce.hosinsa.entity.member.Member

interface MemberQueryRepository {
    fun findMembersWithBirthdayToday(): MutableList<Member>
}
