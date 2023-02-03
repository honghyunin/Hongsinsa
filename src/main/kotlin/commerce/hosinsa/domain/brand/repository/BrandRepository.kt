package commerce.hosinsa.domain.brand.repository

import commerce.hosinsa.domain.brand.entity.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository: JpaRepository<Brand, Int> {

    fun findByName(name: String): Brand?
    fun existsByName(name: String): Boolean
}