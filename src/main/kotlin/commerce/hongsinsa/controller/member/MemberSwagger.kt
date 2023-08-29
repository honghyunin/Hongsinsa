package commerce.hongsinsa.controller.member

import commerce.hongsinsa.dto.member.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "회원관리", description = "회원관리 API")
interface MemberSwagger {

    @Operation(summary = "회원가입", description = "회원가입 API")
    fun signUp(signUpDto: SignUpDto): ResponseEntity<Any>

    @Operation(summary = "로그인", description = "회원 로그인 API")
    fun signIn(signInDto: SignInDto): ResponseEntity<Any>

    @Operation(summary = "프로필 변경", description = "회원의 프로필을 변경")
    fun patchProfile(memberIdx: Int, patchProfileDto: PatchProfileDto): ResponseEntity<Any>

    @Operation(summary = "패스워드 변경", description = "회원 패스워드 변경 API")
    fun changePassword(memberIdx: Int, changePasswordDto: ChangePasswordDto): ResponseEntity<Any>
}