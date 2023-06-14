package commerce.hongsinsa.brand

import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.BRAND_NOT_FOUND
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify

internal class BrandServiceTest : DescribeSpec({
    describe("brandAvailable") {
        context("존재하지 않는 브랜드가 입력될 경우") {

            every { brandService.brandAvailable(brandAvailableDto) } just Runs

            it("브랜드 입점 심사를 등록한다") {
                brandService.brandAvailable(brandAvailableDto)

                verify(exactly = 1) { brandService.brandAvailable(brandAvailableDto) }
            }
        }

        context("이미 존재하는 브랜드가 입력될 경우") {
            every { brandService.brandAvailable(brandAvailableDto) } throws CustomException(BRAND_NOT_FOUND)

            it("Brand Not Found Exception이 발생한다") {
                shouldThrow<CustomException> {
                    brandService.brandAvailable(brandAvailableDto)
                }
            }
        }
    }

    describe("auditAvailable") {

        context("입점 기준에 부합하는 브랜드가 입력될 경우") {

            every { brandService.auditAvailable(BRAND) } just Runs

            it("입점 심사에 통과한다") {
                brandService.auditAvailable(BRAND)

                verify(exactly = 1) { brandService.auditAvailable(BRAND) }
            }
        }
        context("입점 기준에 부합하지 않을 경우") {

            every { brandService.auditAvailable(BRAND) } throws CustomException(BRAND_NOT_FOUND)

            it("Brand Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { brandService.auditAvailable(BRAND) }
            }
        }
    }

    describe("brandUpdate") {

        context("존재하는 브랜드 정보를 입력될 경우") {
            every { brandService.brandUpdate(BRAND, updateBrandDto) } just Runs

            it("브랜드 정보 업데이트에 성공한다") {
                brandService.brandUpdate(BRAND, updateBrandDto)

                verify(exactly = 1) { brandService.brandUpdate(BRAND, updateBrandDto) }
            }
        }

        context("존재하지 않는 브랜드를 입력될 경우") {
            every { brandService.brandUpdate(BRAND, updateBrandDto) } throws CustomException(BRAND_NOT_FOUND)

            it("Brand Not Found Exceptio이 발생한다") {
                shouldThrow<CustomException> { brandService.brandUpdate(BRAND, updateBrandDto) }
            }
        }
    }
})