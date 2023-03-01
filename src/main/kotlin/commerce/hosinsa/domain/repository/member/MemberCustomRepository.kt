package commerce.hosinsa.domain.repository.member

import commerce.hosinsa.entity.member.Member

interface MemberCustomRepository {
    fun findMembersWithBirthdayToday(): MutableList<Member>
}
