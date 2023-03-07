package commerce.hosinsa.domain.brand

import commerce.hosinsa.domain.brand.NAME
import commerce.hosinsa.domain.brand.brandAvailableDto
import commerce.hosinsa.domain.brand.brandService
import commerce.hosinsa.domain.brand.brandUpdateDto
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.BRAND_NOT_FOUND
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*

internal class BrandServiceTest : DescribeSpec({
    describe("brandAvailable") {
        context("존재하지 않는 브랜드가 입력되면") {

            every { brandService.brandAvailable(brandAvailableDto) } just Runs

            it("브랜드 입점 심사를 등록한다") {
                brandService.brandAvailable(brandAvailableDto)

                verify(exactly = 1) { brandService.brandAvailable(brandAvailableDto) }
            }
        }

        context("이미 존재하는 브랜드가 입력되면") {
            every { brandService.brandAvailable(brandAvailableDto) } throws CustomException(BRAND_NOT_FOUND)

            it("브랜드 입점 심사 등록에 실패한다") {
                shouldThrow<CustomException> {
                    brandService.brandAvailable(brandAvailableDto)
                }
            }
        }
    }

    describe("auditAvailable") {

        context("입점 기준에 부합하는 브랜드가 입력되면") {

            every { brandService.auditAvailable(NAME) } just Runs

            it("입점 심사에 통과한다") {
                brandService.auditAvailable(NAME)

                verify(exactly = 1) { brandService.auditAvailable(NAME) }
            }
        }
        context("입점 기준에 부합하지 않을 경우") {

            every { brandService.auditAvailable(NAME) } throws CustomException(BRAND_NOT_FOUND)

            it("입점 심사에 탈락한다") {
                shouldThrow<CustomException> { brandService.auditAvailable(NAME) }
            }
        }
    }

    describe("brandUpdate") {

        context("존재하는 브랜드 정보를 입력받을 경우") {
            every { brandService.brandUpdate(brandUpdateDto) } just Runs

            it("브랜드 정보 업데이트에 성공한다") {
                brandService.brandUpdate(brandUpdateDto)

                verify(exactly = 1) { brandService.brandUpdate(brandUpdateDto) }
            }
        }

        context("존재하지 않는 브랜드를 입력받을 경우") {
            every { brandService.brandUpdate(brandUpdateDto) } throws CustomException(BRAND_NOT_FOUND)

            it("브랜드 정보 업데이트에 실패한다") {
                shouldThrow<CustomException> { brandService.brandUpdate(brandUpdateDto) }
            }
        }
    }
})