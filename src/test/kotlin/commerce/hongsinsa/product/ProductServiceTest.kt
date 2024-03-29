package commerce.hongsinsa.product

import commerce.hongsinsa.brand.BRAND
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify

internal class ProductServiceTest : DescribeSpec({

    describe("registrationProduct") {

        context("존재하는 브랜드 이름이 입력되면") {
            every { brandRepository.findByNameAndIsDeleteFalse(REGISTRATION_PRODUCT_DTO.brandName!!) } returns BRAND
            every { productService.registrationProduct(PRODUCT) } just Runs

            it("상품 등록에 성공한다") {
                productService.registrationProduct(PRODUCT)

                verify(exactly = 1) { productService.registrationProduct(PRODUCT) }
            }
        }

        context("상품이 이미 존재할 경우") {
            every { productService.registrationProduct(PRODUCT) } throws CustomException(
                PRODUCT_IS_ALREADY_EXISTS
            )

            it("Product Already Exists Exception이 발생한다") {
                shouldThrow<CustomException> { productService.registrationProduct(PRODUCT) }
            }
        }

        context("브랜드가 존재하지 않을 경우") {
            every { productService.registrationProduct(PRODUCT) } throws CustomException(
                BRAND_NOT_FOUND
            )

            it("Brand Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { productService.registrationProduct(PRODUCT) }
            }
        }
    }

    describe("updateProduct") {

        context("유효한 인자값 UpdateProductDto가 입력될 경우") {
            every { productService.updateProduct(UPDATE_PRODUCT_DTO) } just Runs

            it("상품 업데이트에 성공한다") {
                productService.updateProduct(UPDATE_PRODUCT_DTO)

                verify(exactly = 1) { productService.updateProduct(UPDATE_PRODUCT_DTO) }
            }
        }

        context("상품이 존재하지 않을 경우") {
            every { productService.updateProduct(UPDATE_PRODUCT_DTO) } throws CustomException(
                PRODUCT_NOT_FOUND
            )

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { productService.updateProduct(UPDATE_PRODUCT_DTO) }
            }
        }
    }

    describe("updateIsSoldOut") {

        context("유효한 ProductIdx가 입력될 경우") {
            every { productService.updateIsSoldOut(PRODUCT_IDX) } just Runs

            it("재고 품절 업데이트에 성공한다") {
                productService.updateIsSoldOut(PRODUCT_IDX)

                verify(exactly = 1) { productService.updateIsSoldOut(PRODUCT_IDX) }
            }
        }

        context("상품이 존재하지 않을 경우") {
            every { productService.updateIsSoldOut(PRODUCT_IDX) } throws CustomException(
                PRODUCT_NOT_FOUND
            )

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { productService.updateIsSoldOut(PRODUCT_IDX) }
            }
        }
    }

    describe("getProduct") {

        context("유효한 ProductIdx가 입력될 경우") {
            every { productService.getProductResponse(PRODUCT_IDX) } returns PRODUCT_RESPONSE

            it("상품 단일 조회에 성공한다") {

                val product = productService.getProductResponse(PRODUCT_IDX)

                product.name shouldBe PRODUCT_NAME
                product.price shouldBe PRICE
                product.brand shouldBe BRAND_NAME
            }
        }

        context("상품이 존재하지 않을 경우") {
            every { productService.getProductResponse(PRODUCT_IDX) } throws CustomException(PRODUCT_NOT_FOUND)

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { productService.getProductResponse(PRODUCT_IDX) }
            }
        }
    }

    describe("getProducts") {

        context("유효한 GetProductFilterDto가 입력될 경우") {
            every { productService.getProducts(GET_PRODUCT_FILTER_DTO, pageable) } returns productPage

            it("상품 전체 태그조회에 성공한다") {

                val products = productService.getProducts(GET_PRODUCT_FILTER_DTO, pageable)

                products shouldBe productPage
            }
        }
    }
})