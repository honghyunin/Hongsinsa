package commerce.hongsinsa.domain.repository

import commerce.hongsinsa.entity.member.Member

interface MemberCustomRepository {
    fun findMembersWithBirthdayToday(): MutableList<Member>
}
