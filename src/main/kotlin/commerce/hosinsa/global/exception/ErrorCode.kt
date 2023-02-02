package commerce.hosinsa.global.exception

enum class ErrorCode(val status: Int, val msg: String) {

    // User Exception
    USER_NOT_FOUND(400, "User Not Found"),
    USER_ALREADY_EXISTS(400, "User Already Exists"),

    CHANGE_PASSWORD_NOT_MATCH(400, "New Password and RePassword Do Not Match"),
    // Authentication Exception
    PASSWORD_NOT_MATCH(400, "Password Does Not Match"),

    // IO Exception
    INPUT_VALUE_NOT_FOUND(400, "Input Value Not Found")
}