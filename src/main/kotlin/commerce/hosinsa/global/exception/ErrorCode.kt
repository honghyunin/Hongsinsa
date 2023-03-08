package commerce.hosinsa.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class ErrorCode(val status: HttpStatus, val msg: String) {

    // Order Exception
    ORDER_NOT_FOUND(NOT_FOUND, "Order Not Found"),
    PRODUCT_QUANTITY_NOT_FOUND(NOT_FOUND, "Product Quantity Not Found"),

    // Coupon Exception
    COUPON_NOT_FOUND(NOT_FOUND, "Coupon Not Found"),

    // Product Exception
    PRODUCT_NOT_FOUND(NOT_FOUND, "Product Not Found"),
    PRODUCT_IS_ALREADY_EXISTS(CONFLICT, "Product Already Exists"),

    // Brand Exception
    BRAND_NOT_FOUND(NOT_FOUND, "Brand Not Found"),
    BRAND_ALREADY_EXISTS(CONFLICT, "Brand Already Exists"),

    // Member Exception
    MEMBER_NOT_FOUND(NOT_FOUND, "Member Not Found"),
    MEMBER_ALREADY_EXISTS(CONFLICT, "Member Already Exists"),

    CHANGE_PASSWORD_NOT_MATCH(BAD_REQUEST, "New Password and RePassword Do Not Match"),

    // Authentication Exception
    PASSWORD_NOT_MATCH(BAD_REQUEST, "Password Does Not Match"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Unauthorized Access"),

    // IO Exception
    INPUT_VALUE_NOT_FOUND(NOT_FOUND, "Input Value Not Found")
}