package commerce.hosinsa.domain.baseTime.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity(

    @CreatedDate @Column(name = "create_at", nullable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @LastModifiedDate @Column(name = "update_at", nullable = false)
    var updatedAt: LocalDateTime? = null
)