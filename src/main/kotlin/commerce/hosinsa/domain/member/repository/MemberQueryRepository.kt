package commerce.hosinsa.domain.member.repository

import commerce.hosinsa.domain.member.entity.Member

interface MemberQueryRepository {
    fun findMembersWithBirthdayToday(): MutableList<Member>
}
