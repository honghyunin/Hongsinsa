package commerce.hongsinsa.repository.request

import commerce.hongsinsa.entity.request.Request
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository: JpaRepository<Request, Int>