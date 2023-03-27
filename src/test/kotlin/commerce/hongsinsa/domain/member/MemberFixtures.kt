package commerce.hongsinsa.domain.member

import commerce.hongsinsa.domain.dto.member.*
import commerce.hongsinsa.domain.repository.member.MemberRepository
import commerce.hongsinsa.domain.service.MemberService
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.extension.toMember
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
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

const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"

fun failSignIn() = shouldThrow<CustomException> {
    memberService.signIn(signInDto)
}

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

val tokenResponse = TokenResponse(
    accessToken = ACCESS_TOKEN,
    refreshToken = REFRESH_TOKEN,
    id = ID,
    idx = MEMBER_IDX
)

val profileUpdateDto = UpdateProfileDto(
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

val MEMBER: Member = signUpDto.toMember()
val OPTIONAL_MEMBER = Optional.of(MEMBER)

const val CHANGED_PW = PASSWORD + "1234"

val memberRepository = mockk<MemberRepository>()
val memberService = mockk<MemberService>()