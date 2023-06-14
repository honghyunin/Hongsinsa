package commerce.hongsinsa.member

import commerce.hongsinsa.config.utils.TokenUtils
import commerce.hongsinsa.dto.member.*
import commerce.hongsinsa.repository.member.MemberRepository
import commerce.hongsinsa.service.member.MemberService
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.member.Role
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import commerce.hongsinsa.extension.toMember
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.util.*

const val MEMBER_IDX = 1
const val EMAIL = "email1234@gmail.com"
const val NAME = "name"
const val NICKNAME = "nickname"
const val ID = "ID1234"
const val PASSWORD = "R@ANDOM!@#PASSWORD"
const val WEIGHT: Short = 60
const val HEIGHT: Short = 178
const val GENDER: Char = 'M'
const val AGE: Short = 20
const val ADDRESS = "Address"
const val PHONE_NUMBER = "01000000000"
val BIRTHDAY: LocalDate = LocalDate.of(2000, 1, 1)
const val RE_NEW_PASSWORD = PASSWORD + 12312
const val ENCODED_PASSWORD = PASSWORD + 12312

const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"

val signUpDto = SignUpDto(
    email = EMAIL,
    id = ID,
    password = PASSWORD,
    birthday = BIRTHDAY,
    name = NAME,
    nickname = NICKNAME,
    weight = WEIGHT,
    height = HEIGHT,
    gender = GENDER,
    age = AGE,
    phoneNumber = PHONE_NUMBER,
    address = ADDRESS
)

val signInDto = SignInDto(
    id = ID,
    password = PASSWORD
)

val tokenDto = TokenDto(
    accessToken = ACCESS_TOKEN,
    refreshToken = REFRESH_TOKEN,
    id = ID,
    idx = MEMBER_IDX
)

val profileUpdateDto = PatchProfileDto(
    password = PASSWORD,
    name = NAME,
    nickname = NICKNAME,
    email = EMAIL,
    weight = WEIGHT,
    height = HEIGHT,
    phoneNumber = PHONE_NUMBER
)

val changePasswordDto = ChangePasswordDto(
    currentPassword = PASSWORD,
    newPassword = PASSWORD + "1234",
    reNewPassword = PASSWORD + "1234"
)

val notMatchChangePasswordDto = ChangePasswordDto(
    currentPassword = PASSWORD,
    newPassword = PASSWORD + "12345",
    reNewPassword = PASSWORD + "1234"
)

val notMatchCurrentChangePasswordDto = ChangePasswordDto(
    currentPassword = PASSWORD + "1111",
    newPassword = PASSWORD + "12345",
    reNewPassword = PASSWORD + "1234"
)

fun getRoleMember(roles: MutableList<Role>): MutableList<Role> =
    roles.filter { it != Role.MEMBER }.toMutableList().also { it.add(Role.MEMBER) }

fun throwExceptionIfIsNotMatchPassword(rawPassword: String, encodedPassword: String) {
    if (notMatchesPassword(rawPassword, encodedPassword))
        throw CustomException(ErrorCode.PASSWORD_NOT_MATCH)
}

fun notMatchesPassword(rawPassword: String, encodedPassword: String): Boolean =
    !passwordEncoder.matches(rawPassword, encodedPassword)

val MEMBER: Member = signUpDto.toMember()
val OPTIONAL_MEMBER = Optional.of(MEMBER)

const val CHANGED_PW = PASSWORD + "1234"

val memberRepository = mockk<MemberRepository>()
val memberService = mockk<MemberService>()
val passwordEncoder = mockk<PasswordEncoder>()
val tokenUtils = mockk<TokenUtils>()