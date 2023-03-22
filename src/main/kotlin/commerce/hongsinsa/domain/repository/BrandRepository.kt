package commerce.hongsinsa.domain.repository

import commerce.hongsinsa.entity.brand.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository: JpaRepository<Brand, Int> {
    fun findByNameAndIsDeleteFalse(name: String): Brand?
    fun existsByNameAndIsDeleteFalse(name: String): Boolean
}