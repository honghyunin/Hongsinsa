package commerce.hongsinsa.global.exception.handler

import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorResponse
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