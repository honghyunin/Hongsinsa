package commerce.hosinsa.domain.member.fixtures

import commerce.hosinsa.domain.member.dto.*
import commerce.hosinsa.domain.member.service.MemberServiceTest
import commerce.hosinsa.global.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
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
    pw = PASSWORD,
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
    pw = PASSWORD
)

fun getTokenResponse() = TokenResponse(
    accessToken = ACCESS_TOKEN,
    refreshToken = REFRESH_TOKEN,
    id = ID
)

fun failSignIn() = shouldThrow<CustomException> {
    MemberServiceTest.memberService.signIn(MemberServiceTest.signInDto)
}

fun getProfileUpdateDto() = ProfileUpdateDto(
    pw = PASSWORD,
    name = NAME,
    nickname = NICKNAME,
    email = EMAIL,
    weight = WEIGHT,
    height = HEIGHT,
    phoneNumber = PHONE_NUMBER
)

fun getPWChangeDto() = PWChangeDto(
    currentPW = PASSWORD,
    newPW = PASSWORD + "1234",
    reNewPW = PASSWORD + "1234"
)

fun getNotMatchPWChangeDto() = PWChangeDto(
    currentPW = PASSWORD,
    newPW = PASSWORD + "12345",
    reNewPW = PASSWORD + "1234"
)

fun getNotMatchCurrentPWChangeDto() = PWChangeDto(
    currentPW = PASSWORD + "1111",
    newPW = PASSWORD + "12345",
    reNewPW = PASSWORD + "1234"
)