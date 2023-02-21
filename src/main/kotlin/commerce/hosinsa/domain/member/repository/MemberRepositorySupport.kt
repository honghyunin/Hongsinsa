package commerce.hosinsa.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.member.entity.QMember.member
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MemberRepositorySupport(private val queryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(Member::class.java), MemberQueryRepository {

    override fun findMembersWithBirthdayToday(): MutableList<Member> {
        val today = LocalDate.now()

        return queryFactory.selectFrom(member)
            .where(member.birthday.dayOfMonth().eq(today.dayOfMonth),
                member.birthday.month().eq(today.month.value))
            .fetch()
            .toMutableList()
    }

}