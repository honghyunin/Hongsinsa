package commerce.hongsinsa.member

import commerce.hongsinsa.dto.member.TokenDto
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.*
import commerce.hongsinsa.extension.toMember
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

internal class MemberServiceTest : DescribeSpec({

    describe("signUp") {
        context("올바른 회원 정보가 입력되면") {
            every { memberRepository.existsByIdAndIsDeleteFalse(signUpDto.id) } returns false
            every { memberService.save(signUpDto.toMember()) } just runs
            every { memberRepository.findByIdAndIsDeleteFalse(signUpDto.id) } returns MEMBER

            val findMember = memberRepository.findByIdAndIsDeleteFalse(signUpDto.id)!!

            it("회원가입이 성공한다") {
                findMember.id shouldBe MEMBER.id
                findMember.email shouldBe MEMBER.email
            }
        }

        context("이미 존재하는 회원 정보가 입력되면") {
            every { memberRepository.existsByIdAndIsDeleteFalse(signUpDto.id) } throws CustomException(MEMBER_ALREADY_EXISTS)

            it("회원가입에 실패한다") {
                shouldThrow<CustomException> {
                    memberRepository.existsByIdAndIsDeleteFalse(signUpDto.id)
                }
            }
        }
    }

    describe("signIn") {
        context("올바른 회원 정보가 입력되면") {

            it("로그인이 성공한다") {
                every { memberService.findByIdAndIsDeleteFalse(signInDto.id) } returns MEMBER

                val findMember = memberService.findByIdAndIsDeleteFalse(signInDto.id)

                every { tokenUtils.createAccessToken(findMember.id, getRoleMember(findMember.roles)) } returns ACCESS_TOKEN
                every { tokenUtils.createRefreshToken(findMember.id, getRoleMember(findMember.roles)) } returns REFRESH_TOKEN

                val accessToken = tokenUtils.createAccessToken(findMember.id, getRoleMember(findMember.roles))
                val refreshToken = tokenUtils.createRefreshToken(findMember.id, getRoleMember(findMember.roles))

                accessToken shouldBe ACCESS_TOKEN
                refreshToken shouldBe REFRESH_TOKEN
            }
        }

        context("회원정보가") {

            context("존재하지 않을 경우") {
                every { memberService.findByIdAndIsDeleteFalse(signInDto.id) } throws CustomException(MEMBER_NOT_FOUND)

                it("로그인에 실패한다") {
                    shouldThrow<CustomException> {
                        memberService.findByIdAndIsDeleteFalse(signInDto.id)
                    }
                }
            }

            context("일치하지 않을 경우") {

                every { notMatchesPassword(PASSWORD, ENCODED_PASSWORD) } throws CustomException(PASSWORD_NOT_MATCH)

                it("로그인에 실패한다") {
                    shouldThrow<CustomException> {
                        notMatchesPassword(PASSWORD, ENCODED_PASSWORD)
                    }
                }
            }
        }
    }

    describe("profileUpdate") {

        context("올바른 회원 정보가 입력되면") {

            every { memberService.patchProfile(MEMBER, profileUpdateDto) } just Runs

            it("회원정보가 변경된다") {
                memberService.patchProfile(MEMBER, profileUpdateDto)

                verify(exactly = 1) { memberService.patchProfile(MEMBER, profileUpdateDto) }
            }
        }

        context("올바르지 않은 회원 정보가 입력되면") {
            every { memberService.patchProfile(MEMBER, profileUpdateDto) } throws CustomException(MEMBER_NOT_FOUND)

            it("회원정보 변경에 실패한다") {
                shouldThrow<CustomException> {
                    memberService.patchProfile(MEMBER, profileUpdateDto)
                }
            }
        }
    }

    describe("pwChange") {

        context("올바른 패스워드가 입력되면") {
            every { memberService.changePassword(MEMBER, RE_NEW_PASSWORD) } just runs

            it("패스워드가 변경된다") {
                memberService.changePassword(MEMBER, RE_NEW_PASSWORD)
                verify(exactly = 1) { memberService.changePassword(MEMBER, RE_NEW_PASSWORD) }
            }
        }

        context("신규 패스워드와 재입력 비밀번호가 같지 않을 때") {
            every { memberService.changePassword(MEMBER, RE_NEW_PASSWORD) } throws CustomException(
                CHANGE_PASSWORD_NOT_MATCH
            )

            it("패스워드가 변경에 실패한다") {
                shouldThrow<CustomException> {
                    memberService.changePassword(MEMBER, RE_NEW_PASSWORD)
                }
            }
        }

        context("기존 패스워드가 일치하지 않을 때") {

            every { memberService.changePassword(MEMBER, RE_NEW_PASSWORD) } throws CustomException(
                CHANGE_PASSWORD_NOT_MATCH
            )

            it("패스워드가 변경에 실패한다") {
                shouldThrow<CustomException> {
                    memberService.changePassword(MEMBER, RE_NEW_PASSWORD)
                }
            }
        }
    }
})