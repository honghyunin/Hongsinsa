package commerce.hosinsa.domain.product.service

import commerce.hosinsa.domain.product.fixtures.*
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify

internal class ProductServiceTest : DescribeSpec({

    describe("registrationProduct") {

        context("올바른 인자값을 입력받을 경우") {

            every { productService.registrationProduct(registrationProductDto) } just Runs

            it("상품 등록에 성공한다") {
                productService.registrationProduct(registrationProductDto)

                verify(exactly = 1) { productService.registrationProduct(registrationProductDto) }
            }
        }

        context("상품이 이미 존재할 경우") {

            every { productService.registrationProduct(registrationProductDto) } throws CustomException(
                PRODUCT_IS_ALREADY_EXISTS
            )

            it("상품 등록에 실패한다") {
                shouldThrow<CustomException> { productService.registrationProduct(registrationProductDto) }
            }
        }

        context("브랜드가 존재하지 않을 경우") {

            every { productService.registrationProduct(registrationProductDto) } throws CustomException(
                BRAND_NOT_FOUND
            )

            it("상품 등록에 실패한다") {
                shouldThrow<CustomException> { productService.registrationProduct(registrationProductDto) }
            }
        }
    }

    describe("updateProduct") {

        context("올바른 인자값을 받을 경우") {
            every { productService.updateProduct(updateProductDto) } just Runs

            it("상품 업데이트에 성공한다") {
                productService.updateProduct(updateProductDto)

                verify(exactly = 1) { productService.updateProduct(updateProductDto) }
            }
        }

        context("상품이 존재하지 않을 경우") {

            every { productService.updateProduct(updateProductDto) } throws CustomException(
                PRODUCT_NOT_FOUND
            )

            it("상품 업데이트에 실패한다") {
                shouldThrow<CustomException> { productService.updateProduct(updateProductDto) }
            }
        }
    }

    describe("updateIsSoldOut") {

        context("올바른 인자값을 받을 경우") {

            every { productService.updateIsSoldOut(PRODUCT_NAME) } just Runs

            it("재고 품절 업데이트에 성공한다") {
                productService.updateIsSoldOut(PRODUCT_NAME)

                verify(exactly = 1) { productService.updateIsSoldOut(PRODUCT_NAME) }
            }
        }

        context("상품이 존재하지 않을 경우") {

            every { productService.updateIsSoldOut(PRODUCT_NAME) } throws CustomException(
                PRODUCT_NOT_FOUND
            )

            it("상품 업데이트에 실패한다") {
                shouldThrow<CustomException> { productService.updateIsSoldOut(PRODUCT_NAME) }
            }
        }
    }

    describe("getProduct") {

        context("올바른 인자값을 받을 경우") {

            every { productService.getProduct(PRODUCT_ID) } returns productResponse

            it("상품 단일 조회에 성공한다") {

                val product = productService.getProduct(PRODUCT_ID)

                product.name shouldBe PRODUCT_NAME
                product.price shouldBe PRICE
                product.brand shouldBe BRAND_NAME
            }
        }

        context("상품이 존재하지 않을 경우") {

            every { productService.getProduct(PRODUCT_ID) } throws CustomException(PRODUCT_NOT_FOUND)

            it("상품 단일 조회에 실패한다") {
                shouldThrow<CustomException> { productService.getProduct(PRODUCT_ID) }
            }
        }
    }
}) {
}