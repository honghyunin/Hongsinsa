package commerce.hongsinsa.exception.handler

import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    protected fun handleBaseException(exception: CustomException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(exception.errorCode.status)
            .body(ErrorResponse(exception.errorCode.status, exception.errorCode.msg))
    }
}