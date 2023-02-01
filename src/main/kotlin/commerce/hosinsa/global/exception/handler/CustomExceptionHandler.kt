package commerce.hosinsa.global.exception.handler

import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorResponse
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