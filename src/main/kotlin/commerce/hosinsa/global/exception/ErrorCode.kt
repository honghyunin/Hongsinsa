package commerce.hosinsa.global.exception

enum class ErrorCode(val status: Int, val msg: String) {

    // Coupon Exception
    COUPON_NOT_FOUND(404, "Coupon Not Found"),

    // Product Exception
    PRODUCT_NOT_FOUND(404, "Product Not Found"),
    PRODUCT_IS_ALREADY_EXISTS(409, "Product Already Exists"),

    // Brand Exception
    BRAND_NOT_FOUND(404, "Brand Not Found"),
    BRAND_ALREADY_EXISTS(409, "Brand Already Exists"),

    // Member Exception
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_ALREADY_EXISTS(409, "Member Already Exists"),

    CHANGE_PASSWORD_NOT_MATCH(400, "New Password and RePassword Do Not Match"),

    // Authentication Exception
    PASSWORD_NOT_MATCH(400, "Password Does Not Match"),

    // IO Exception
    INPUT_VALUE_NOT_FOUND(404, "Input Value Not Found")
}