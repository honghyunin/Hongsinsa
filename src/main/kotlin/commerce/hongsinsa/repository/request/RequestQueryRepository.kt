package commerce.hongsinsa.repository.request

import com.querydsl.jpa.impl.JPAQueryFactory
import commerce.hongsinsa.entity.request.QRequest.request
import commerce.hongsinsa.entity.request.Request
import commerce.hongsinsa.entity.request.RequestStatus
import org.springframework.stereotype.Repository

@Repository
class RequestQueryRepository(private val queryFactory: JPAQueryFactory) {

    fun findByIdxAndStatusOrderReceived(requestIdx: Int): Request? = queryFactory.selectFrom(request)
        .where(request.idx.eq(requestIdx)
            .and(request.status.eq(RequestStatus.Request_RECEIVED)))
        .fetchOne()
}