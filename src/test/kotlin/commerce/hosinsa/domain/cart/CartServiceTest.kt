package commerce.hosinsa.domain.cart

import commerce.hosinsa.domain.member.MEMBER_IDX
import commerce.hosinsa.domain.member.OPTIONAL_MEMBER
import commerce.hosinsa.domain.product.PRODUCT
import commerce.hosinsa.domain.product.PRODUCT_IDX
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import commerce.hosinsa.global.exception.ErrorCode.PRODUCT_NOT_FOUND
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify

class CartServiceTest : DescribeSpec({

    describe("addProduct") {

        context("유효한 AddProductDto가 입력될 경우") {
            every { memberRepository.findById(MEMBER_IDX) } returns OPTIONAL_MEMBER
            every { productRepository.findByIdx(PRODUCT_IDX) } returns PRODUCT
            every { cartRepository.save(CART) } returns CART
            every { cartService.addProduct(ADD_PRODUCT_DTO) } just Runs

            cartService.addProduct(ADD_PRODUCT_DTO)
            it("장바구니에 담기가 성공한다") {
                verify(exactly = 1) { cartService.addProduct(ADD_PRODUCT_DTO) }
            }
        }

        context("존재하지 않는 회원이 요청할 경우") {
            every { memberRepository.findById(MEMBER_IDX) } throws CustomException(MEMBER_NOT_FOUND)

            it("Member Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { memberRepository.findById(MEMBER_IDX) }
            }
        }

        context("상품이 없을 경우") {
            every { productRepository.findByIdx(PRODUCT_IDX) } throws CustomException(PRODUCT_NOT_FOUND)

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { productRepository.findByIdx(PRODUCT_IDX) }
            }
        }
    }

    describe("getCart") {

        context("존재하지 않는 회원이 입력될 경우") {
            every { memberRepository.existsById(MEMBER_IDX) } returns false
            every { cartCustomRepository.findProductsByMemberIdx(MEMBER_IDX) } returns CART_RESPONSE_LIST
            every { cartService.getCart(MEMBER_IDX) } returns CART_RESPONSE_LIST

            cartService.getCart(MEMBER_IDX)
            it("장바구니 조회에 성공한다") {
                verify(exactly = 1) { cartService.getCart(MEMBER_IDX) }
            }
        }

        context("존재하는 회원이 입력될 경우") {
            every { memberRepository.existsById(MEMBER_IDX) } returns true

            it("Member Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { memberRepository.findById(MEMBER_IDX) }
            }
        }
    }

    describe("deleteCartProduct") {

        context("존재하는 상품일 경우") {
            every { cartRepository.findByProductIdx(PRODUCT_IDX) } returns CART
            every { cartRepository.delete(CART) } just Runs
            every { cartService.deleteCartProduct(PRODUCT_IDX) } just Runs

            cartService.deleteCartProduct(PRODUCT_IDX)
            it("장바구니에 담긴 상품이 삭제된다") {
                verify(exactly = 1) { cartService.deleteCartProduct(PRODUCT_IDX) }
            }
        }

        context("존재하지 않는 상품일 경우") {
            every { cartRepository.findByProductIdx(PRODUCT_IDX) } throws CustomException(PRODUCT_NOT_FOUND)

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { cartRepository.findByProductIdx(PRODUCT_IDX) }
            }
        }
    }
})