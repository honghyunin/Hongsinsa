package commerce.hongsinsa.repository.member

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.member.QMember.member
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MemberQueryRepository(private val queryFactory: JPAQueryFactory) {

    fun findMembersWithBirthdayToday(): MutableList<Member> {
        val today = LocalDate.now()

        return queryFactory.selectFrom(member)
            .where(
                member.birthday.dayOfMonth().eq(today.dayOfMonth),
                member.birthday.month().eq(today.month.value)
            )
            .fetch()
            .toMutableList()
    }
}