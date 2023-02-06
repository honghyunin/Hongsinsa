package commerce.hosinsa.domain.brand.dto

class AvailableBrandDto(
    val name: String,
    val email: String,
    val homepageUrl: String,
    val introduce: String,
    val company: String,
    val businessNumber: String,
    val businessAddress: String,
    val managerName: String,
    val phoneNumber: String,
    val isAudit: Boolean = false
)
