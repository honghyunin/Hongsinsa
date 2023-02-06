package commerce.hosinsa.domain.member.service

import commerce.hosinsa.domain.member.fixtures.*
import commerce.hosinsa.domain.member.repository.MemberRepository
import commerce.hosinsa.global.config.utils.toMember
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

internal class MemberServiceTest : DescribeSpec({

    describe("signUp") {
        context("올바른 회원 정보가 입력되면") {
            every { memberRepository.existsById(signUpDto.id) } returns false
            every { memberService.signUp(signUpDto) } just Runs

            memberService.signUp(signUpDto)

            every { memberRepository.findById(signUpDto.id) } returns member

            val findMember = memberRepository.findById(signUpDto.id)!!

            it("회원가입이 성공한다") {
                signUpDto.id shouldBe findMember.id
                signUpDto.email shouldBe findMember.email
            }
        }

        context("이미 존재하는 회원 정보가 입력되면") {
            every { memberRepository.existsById(signUpDto.id) } returns true

            every { memberService.signUp(signUpDto) } throws CustomException(USER_ALREADY_EXISTS)

            it("회원가입에 실패한다") {
                shouldThrow<CustomException> {
                    memberService.signUp(signUpDto)
                }
            }
        }
    }

    describe("signIn") {
        context("올바른 회원 정보가 입력되면") {
            every { memberService.signIn(signInDto) } returns tokenResponse

            it("로그인이 성공한다") {
                val token = memberService.signIn(signInDto)

                token shouldBe tokenResponse
            }
        }

        context("회원정보가") {

            context("존재하지 않을 경우") {

                every { memberService.signIn(signInDto) } throws CustomException(USER_NOT_FOUND)

                it("로그인에 실패한다") {
                    failSignIn()
                }
            }

            context("일치하지 않을 경우") {

                every { memberService.signIn(signInDto) } throws CustomException(PASSWORD_NOT_MATCH)

                it("로그인에 실패한다") {
                    failSignIn()
                }
            }
        }
    }

    describe("profileUpdate") {

        context("올바른 회원 정보가 입력되면") {

            every { memberService.profileUpdate(profileUpdateDto) } just Runs

            it("회원정보가 변경된다") {
                memberService.profileUpdate(profileUpdateDto)

                verify(exactly = 1) { memberService.profileUpdate(profileUpdateDto) }
            }
        }

        context("올바르지 않은 회원 정보가 입력되면") {
            every { memberService.profileUpdate(profileUpdateDto) } throws CustomException(USER_NOT_FOUND)

            it("회원정보 변경에 실패한다") {
                shouldThrow<CustomException> {
                    memberService.profileUpdate(profileUpdateDto)
                }
            }
        }
    }

    describe("pwChange") {

        context("올바른 패스워드가 입력되면") {
            every { memberService.pwChange(pwChangeDto) } returns CHANGED_PW

            it("패스워드가 변경된다") {
                val pw = memberService.pwChange(pwChangeDto)

                CHANGED_PW shouldBe pw
            }
        }

        context("신규 패스워드와 재입력 비밀번호가 같지 않을 때") {
            every { memberService.pwChange(notMatchPWChangeDto) } throws CustomException(CHANGE_PASSWORD_NOT_MATCH)

            it("패스워드가 변경에 실패한다") {
                shouldThrow<CustomException> {
                    memberService.pwChange(notMatchPWChangeDto)
                }
            }
        }

        context("기존 패스워드가 일치하지 않을 때") {

            every { memberService.pwChange(notMatchCurrentPWChangeDto) } throws CustomException(CHANGE_PASSWORD_NOT_MATCH)

            it("패스워드가 변경에 실패한다") {
                shouldThrow<CustomException> {
                    memberService.pwChange(notMatchCurrentPWChangeDto)
                }
            }
        }
    }
}) {
    companion object {
        val signUpDto = getSignUpDto()
        val signInDto = getSignInDto()
        val tokenResponse = getTokenResponse()
        val profileUpdateDto = getProfileUpdateDto()
        val pwChangeDto = getPWChangeDto()
        val notMatchPWChangeDto = getNotMatchPWChangeDto()
        val notMatchCurrentPWChangeDto = getNotMatchCurrentPWChangeDto()
        const val CHANGED_PW = PASSWORD + "1234"

        val member = signUpDto.toMember()

        val memberRepository = mockk<MemberRepository>()
        val memberService = mockk<MemberServiceImpl>()
    }
}