package commerce.hongsinsa.controller.member

import commerce.hongsinsa.dto.member.*
import commerce.hongsinsa.service.member.MemberService
import commerce.hongsinsa.entity.member.Role
import commerce.hongsinsa.config.utils.CurrentMemberUtil
import commerce.hongsinsa.config.utils.TokenUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService,
    private val currentMemberUtil: CurrentMemberUtil,
    private val tokenUtils: TokenUtils,
) {

    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpDto: SignUpDto): Unit = memberService.signUp(signUpDto)

    @PostMapping("/signIn")
    fun signIn(@RequestBody signInDto: SignInDto): TokenDto {
        val findMember = memberService.signIn(signInDto)

        return TokenDto(
            accessToken = tokenUtils.createAccessToken(findMember.id, getRoleMember(findMember.roles)),
            refreshToken = tokenUtils.createRefreshToken(findMember.id, getRoleMember(findMember.roles)),
            id = findMember.id,
            idx = findMember.idx!!
        )
    }

    private fun getRoleMember(roles: MutableList<Role>): MutableList<Role> =
        roles.filter { it != Role.MEMBER }.toMutableList().also { it.add(Role.MEMBER) }

    @PutMapping("/update")
    fun updateProfile(@RequestBody updateProfileDto: UpdateProfileDto): Unit =
        memberService.updateProfile(updateProfileDto)

    @PutMapping("/pw/change")
    fun changePassword(@RequestBody changePasswordDto: ChangePasswordDto): String =
        memberService.changePassword(currentMemberUtil.getCurrentMemberIfAuthenticated(), changePasswordDto)
}