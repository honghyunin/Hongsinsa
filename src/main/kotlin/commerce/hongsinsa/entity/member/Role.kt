package commerce.hongsinsa.entity.member

enum class Role(
    val role: String,
    val title: String,
) {

    GUEST("ROLE_GUEST", "게스트"),
    MEMBER("ROLE_MEMBER", "유저"),
    SELLER("ROLE_SELLER", "판매자"),
    ADMIN("ROLE_ADMIN", "어드민")
}