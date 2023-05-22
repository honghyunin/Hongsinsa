package commerce.hongsinsa.controller.member

import commerce.hongsinsa.dto.member.*
import commerce.hongsinsa.service.member.MemberService
import commerce.hongsinsa.entity.member.Role
import commerce.hongsinsa.config.utils.CurrentMemberUtil
import commerce.hongsinsa.config.utils.TokenUtils
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import commerce.hongsinsa.extension.throwIfIsNotMatchesNewPasswordAndReNewPassword
import commerce.hongsinsa.extension.toMember
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService,
    private val tokenUtils: TokenUtils,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<Any> {
        memberService.throwExceptionIfIsExistByMemberId(signUpDto.id)

        signUpDto
            .apply { password = passwordEncoder.encode(this.password) }
            .let { memberService.save(it.toMember()) }

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/signIn")
    fun signIn(@RequestBody signInDto: SignInDto): TokenDto {
        val findMember = memberService.findByIdAndIsDeleteFalse(signInDto.id)
        throwExceptionIfIsNotMatchPassword(signInDto.password, findMember.password)

        return TokenDto(
            accessToken = tokenUtils.createAccessToken(findMember.id, getRoleMember(findMember.roles)),
            refreshToken = tokenUtils.createRefreshToken(findMember.id, getRoleMember(findMember.roles)),
            id = findMember.id,
            idx = findMember.idx!!
        )
    }

    private fun throwExceptionIfIsNotMatchPassword(rawPassword: String, encodedPassword: String) {
        if (notMatchesPassword(rawPassword, encodedPassword))
            throw CustomException(ErrorCode.PASSWORD_NOT_MATCH)
    }

    private fun notMatchesPassword(rawPassword: String, encodedPassword: String): Boolean =
        !passwordEncoder.matches(rawPassword, encodedPassword)

    private fun getRoleMember(roles: MutableList<Role>): MutableList<Role> =
        roles.filter { it != Role.MEMBER }.toMutableList().also { it.add(Role.MEMBER) }

    @PatchMapping("{memberIdx}")
    fun patchProfile(@PathVariable("memberIdx") memberIdx: Int, @RequestBody patchProfileDto: PatchProfileDto): ResponseEntity<Any>{
        val member = memberService.findByMemberIdx(memberIdx)
        patchProfileDto.apply { password = passwordEncoder.encode(password) }
        memberService.patchProfile(member, patchProfileDto)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PatchMapping("/{memberIdx}/password")
    fun changePassword(@PathVariable("memberIdx") memberIdx: Int, @RequestBody changePasswordDto: ChangePasswordDto): ResponseEntity<Any> {
        val member = memberService.findByMemberIdx(memberIdx)
        throwExceptionIfIsNotMatchPassword(changePasswordDto.currentPassword, member.password)
        changePasswordDto.throwIfIsNotMatchesNewPasswordAndReNewPassword()

        val reNewPassword = passwordEncoder.encode(changePasswordDto.reNewPassword)
        memberService.changePassword(member, reNewPassword)

        return ResponseEntity<Any>(HttpStatus.OK)
    }
}