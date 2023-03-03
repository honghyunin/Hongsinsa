package commerce.hosinsa.domain.repository

import commerce.hosinsa.entity.member.Member

interface MemberCustomRepository {
    fun findMembersWithBirthdayToday(): MutableList<Member>
}
