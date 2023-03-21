package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.member.*
import commerce.hosinsa.domain.service.MemberService
import commerce.hosinsa.global.config.utils.CurrentMemberUtil
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/member")
class MemberController(private val memberService: MemberService, private val currentMemberUtil: CurrentMemberUtil) {

    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpDto: SignUpDto): Unit = memberService.signUp(signUpDto)

    @PostMapping("/signIn")
    fun signIn(@RequestBody signInDto: SignInDto): TokenResponse = memberService.signIn(signInDto)

    @PutMapping("/update")
    fun updateProfile(@RequestBody updateProfileDto: UpdateProfileDto): Unit =
        memberService.updateProfile(updateProfileDto)

    @PutMapping("/pw/change")
    fun changePassword(@RequestBody changePasswordDto: ChangePasswordDto): String
     = memberService.changePassword(currentMemberUtil.getCurrentMemberIfAuthenticated(), changePasswordDto)
}