package commerce.hosinsa.domain.member.fixtures

import commerce.hosinsa.domain.member.dto.*
import commerce.hosinsa.domain.member.repository.MemberRepository
import commerce.hosinsa.domain.member.service.MemberServiceImpl
import commerce.hosinsa.global.config.utils.toMember
import commerce.hosinsa.global.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import java.time.LocalDateTime


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
val BIRTHDAY: LocalDateTime = LocalDateTime.of(2000, 1, 1, 0, 0, 0)

const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"

fun getSignUpDto() = SignUpDto(
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

fun getSignInDto() = SignInDto(
    id = ID,
    password = PASSWORD
)

fun getTokenResponse() = TokenResponse(
    accessToken = ACCESS_TOKEN,
    refreshToken = REFRESH_TOKEN,
    id = ID
)

fun failSignIn() = shouldThrow<CustomException> {
    memberService.signIn(signInDto)
}

fun getUpdateProfileDto() = UpdateProfileDto(
    password = PASSWORD,
    name = NAME,
    nickname = NICKNAME,
    email = EMAIL,
    weight = WEIGHT,
    height = HEIGHT,
    phoneNumber = PHONE_NUMBER
)

fun getChangePasswordDto() = ChangePasswordDto(
    currentPassword = PASSWORD,
    newPassword = PASSWORD + "1234",
    reNewPassword = PASSWORD + "1234"
)

fun getNotMatchChangePasswordDto() = ChangePasswordDto(
    currentPassword = PASSWORD,
    newPassword = PASSWORD + "12345",
    reNewPassword = PASSWORD + "1234"
)

fun getNotMatchCurrentChangePasswordDto() = ChangePasswordDto(
    currentPassword = PASSWORD + "1111",
    newPassword = PASSWORD + "12345",
    reNewPassword = PASSWORD + "1234"
)

val signUpDto = getSignUpDto()
val signInDto = getSignInDto()
val tokenResponse = getTokenResponse()
val profileUpdateDto = getUpdateProfileDto()
val changePasswordDto = getChangePasswordDto()
val notMatchChangePasswordDto = getNotMatchChangePasswordDto()
val notMatchCurrentChangePasswordDto = getNotMatchCurrentChangePasswordDto()
const val CHANGED_PW = PASSWORD + "1234"

val member = signUpDto.toMember()

val memberRepository = mockk<MemberRepository>()
val memberService = mockk<MemberServiceImpl>()