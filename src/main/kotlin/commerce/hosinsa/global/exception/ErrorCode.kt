package commerce.hosinsa.global.exception

enum class ErrorCode(val status: Int, val msg: String) {

    // User Exception
    USER_NOT_FOUND(400, "User Not Found"),
    USER_ALREADY_EXISTS(400, "User Already Exists"),

    // Authentication Exception
    PASSWORD_NOT_MATCH(400, "Password Does Not Match"),

    // IO Exception
    INPUT_VALUE_NOT_FOUND(400, "Input Value Not Found")
}