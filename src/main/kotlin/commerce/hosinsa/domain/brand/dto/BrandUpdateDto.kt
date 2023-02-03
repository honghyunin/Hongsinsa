package commerce.hosinsa.domain.brand.dto

class BrandUpdateDto(
    var name: String,
    var email: String,
    var homepageUrl: String,
    var introduce: String,
    var company: String,
    val businessNumber: String,
    var businessAddress: String,
    var managerName: String,
    var phoneNumber: String,
)
