package commerce.hosinsa.domain.member.service

import commerce.hosinsa.domain.member.fixtures.failSignIn
import commerce.hosinsa.domain.member.fixtures.getSignInDto
import commerce.hosinsa.domain.member.fixtures.getSignUpDto
import commerce.hosinsa.domain.member.fixtures.getTokenResponse
import commerce.hosinsa.domain.member.repository.MemberRepository
import commerce.hosinsa.global.config.utils.toMember
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk

internal class MemberServiceTest : DescribeSpec() {

    init {
        this.describe("signUp") {
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

        this.describe("signIn") {
            context("올바른 회원 정보가 입력되면") {
                every { memberService.signIn(signInDto) } returns tokenResponse

                val token = memberService.signIn(signInDto)

                it("로그인이 성공한다") {
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
    }

    companion object {
        val signUpDto = getSignUpDto()
        val signInDto = getSignInDto()
        val tokenResponse = getTokenResponse()

        val member = signUpDto.toMember()

        val memberRepository = mockk<MemberRepository>()
        val memberService = mockk<MemberServiceImpl>()
    }

}