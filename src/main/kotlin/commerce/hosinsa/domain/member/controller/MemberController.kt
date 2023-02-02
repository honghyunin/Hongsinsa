package commerce.hosinsa.domain.member.controller

import commerce.hosinsa.domain.member.dto.*
import commerce.hosinsa.domain.member.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @PutMapping("/update")
    fun profileUpdate(@RequestBody profileUpdateDto: ProfileUpdateDto): Unit = memberService.profileUpdate(profileUpdateDto)

    @PutMapping("/pw/change")
    fun pwChange(@RequestBody pwChangeDto: PWChangeDto): String = memberService.pwChange(pwChangeDto)
}