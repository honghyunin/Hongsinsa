package commerce.hosinsa.domain.member.controller

import commerce.hosinsa.domain.member.dto.SignInDto
import commerce.hosinsa.domain.member.dto.SignUpDto
import commerce.hosinsa.domain.member.dto.TokenResponse
import commerce.hosinsa.domain.member.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(private val memberService: MemberService) {

    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpDto: SignUpDto): Unit = memberService.signUp(signUpDto)

    @PostMapping("/signIn")
    fun signIn(@RequestBody signInDto: SignInDto): TokenResponse = memberService.signIn(signInDto)
}