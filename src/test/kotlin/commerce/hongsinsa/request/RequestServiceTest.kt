package commerce.hongsinsa.request

import commerce.hongsinsa.member.MEMBER_IDX
import commerce.hongsinsa.product.PRODUCT
import commerce.hongsinsa.product.PRODUCT_IDX
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.*
import commerce.hongsinsa.extension.toOrder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import java.util.*

class RequestServiceTest : DescribeSpec({

    describe("request") {

        context("올바른 정보가 입력되면") {
            every { requestRepository.save(REQUEST_DTO.toOrder(currentMemberUtil.currentMember!!)) } returns REQUEST
            every { productRepository.findByIdxAndIsDeleteFalse(PRODUCT_IDX) } returns PRODUCT
            every { requestService.processRequest(REQUEST, REQUEST_DTO) } just Runs

            requestService.processRequest(REQUEST, REQUEST_DTO)

            it("새로운 주문이 생성된다") {
                verify(exactly = 1) { requestService.processRequest(REQUEST, REQUEST_DTO) }
            }

            every { requestProductRepository.save(REQUEST_PRODUCT) } returns REQUEST_PRODUCT

            val requestProduct = requestProductRepository.save(REQUEST_PRODUCT)

            it("상품의 재고 업데이트가 성공한다") {
                REQUEST_PRODUCT shouldBe requestProduct
            }

        }

        context("상품이 존재하지 않을 경우") {
            every { requestRepository.save(any()) } returns REQUEST
            every { productRepository.findByIdxAndIsDeleteFalse(PRODUCT_IDX) } returns null
            every { requestService.processRequest(REQUEST, REQUEST_DTO) } throws CustomException(
                PRODUCT_NOT_FOUND
            )

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { requestService.processRequest(REQUEST, REQUEST_DTO) }
            }
        }

        context("상품의 개수가 null일 경우") {
            if (EMPTY_PRODUCT_QUANTITIES_REQUEST_DTO.productQuantities[PRODUCT_IDX] == null)
                every {
                    requestService.processRequest(
                        REQUEST,
                        EMPTY_PRODUCT_QUANTITIES_REQUEST_DTO
                    )
                } throws CustomException(
                    PRODUCT_QUANTITY_NOT_FOUND
                )

            it("Product Quantity Not Found Exception이 발생한다") {
                shouldThrow<CustomException> {
                    requestService.processRequest(
                        REQUEST,
                        EMPTY_PRODUCT_QUANTITIES_REQUEST_DTO
                    )
                }
            }
        }
    }

    describe("getOrder") {

        context("올바른 정보가 입력되면") {
            every { memberRepository.existsById(MEMBER_IDX) } returns true
            every { requestProductQueryRepository.findGetRequestResponsesByMemberIdx(MEMBER_IDX) } returns GET_REQUEST_RESPONSE_LIST
            every { requestService.getRequests(MEMBER_IDX) } returns GET_REQUEST_RESPONSE_LIST

            val orders = requestService.getRequests(MEMBER_IDX)

            it("주문 조회에 성공한다") {
                GET_REQUEST_RESPONSE_LIST shouldBe orders
            }
        }

        context("존재하지 않는 회원이 입력되면") {
            every { memberRepository.existsById(MEMBER_IDX) } returns false
            every { requestService.getRequests(MEMBER_IDX) } throws CustomException(MEMBER_NOT_FOUND)

            it("Member Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { requestService.getRequests(MEMBER_IDX) }
            }
        }

        context("조회된 주문이 없을 경우") {
            every { requestProductQueryRepository.findGetRequestResponsesByMemberIdx(MEMBER_IDX) } returns mutableListOf()
            every { requestService.getRequests(MEMBER_IDX) } throws CustomException(REQUEST_NOT_FOUND)

            it("Order Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { requestService.getRequests(MEMBER_IDX) }
            }
        }
    }

    describe("cancelOrder") {

        context("올바른 정보가 입력되면") {
            every { requestRepository.findById(REQUEST_IDX) } returns Optional.of(REQUEST)
            every { requestService.findAllByOrderAndIsDeleteFalse(REQUEST) } returns REQUEST_PRODUCT_LIST

            val orderProductList = requestService.findAllByOrderAndIsDeleteFalse(REQUEST)
                .map { orderProduct ->
                    orderProduct.isDelete = true
                    orderProduct
                }

            it("주문이 취소된다") {
                orderProductList[0].isDelete shouldBe true
            }
        }

        context("주문이 없을 경우") {
            every { requestRepository.findById(REQUEST_IDX) } returns Optional.empty()
            every { requestService.findAllByOrderAndIsDeleteFalse(REQUEST) } throws CustomException(REQUEST_NOT_FOUND)

            it("Not Found Order Exception이 발생한다") {
                shouldThrow<CustomException> { requestService.findAllByOrderAndIsDeleteFalse(REQUEST) }
            }
        }
    }
})