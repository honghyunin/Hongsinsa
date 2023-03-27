package commerce.hongsinsa.domain.repository.member

import commerce.hongsinsa.entity.member.Member

interface MemberCustomRepository {
    fun findMembersWithBirthdayToday(): MutableList<Member>
}
