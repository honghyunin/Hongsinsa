package commerce.hongsinsa.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ErrorResponse(
    val status: HttpStatus,
    val msg: String? = null,
) {
    val formatNow: String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd / HH : mm : ss "))
}