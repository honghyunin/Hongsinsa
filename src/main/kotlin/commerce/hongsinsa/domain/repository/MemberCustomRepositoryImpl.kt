package commerce.hongsinsa.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.member.QMember.member
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MemberCustomRepositoryImpl(private val queryFactory: JPAQueryFactory) :
    MemberCustomRepository {

    override fun findMembersWithBirthdayToday(): MutableList<Member> {
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