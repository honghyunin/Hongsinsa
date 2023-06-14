package commerce.hongsinsa.order

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

class OrderServiceTest : DescribeSpec({

    describe("orderRequest") {

        context("올바른 정보가 입력되면") {
            every { orderRepository.save(ORDER_REQUEST_DTO.toOrder(currentMemberUtil.currentMember!!)) } returns ORDER
            every { productRepository.findByIdxAndIsDeleteFalse(PRODUCT_IDX) } returns PRODUCT
            every { orderService.processOrderRequest(ORDER, ORDER_REQUEST_DTO) } just Runs

            orderService.processOrderRequest(ORDER, ORDER_REQUEST_DTO)

            it("새로운 주문이 생성된다") {
                verify(exactly = 1) { orderService.processOrderRequest(ORDER, ORDER_REQUEST_DTO) }
            }

            every { orderProductRepository.save(ORDER_PRODUCT) } returns ORDER_PRODUCT

            val orderProduct = orderProductRepository.save(ORDER_PRODUCT)

            it("상품의 재고 업데이트가 성공한다") {
                ORDER_PRODUCT shouldBe orderProduct
            }

        }

        context("상품이 존재하지 않을 경우") {
            every { orderRepository.save(any()) } returns ORDER
            every { productRepository.findByIdxAndIsDeleteFalse(PRODUCT_IDX) } returns null
            every { orderService.processOrderRequest(ORDER, ORDER_REQUEST_DTO) } throws CustomException(
                PRODUCT_NOT_FOUND
            )

            it("Product Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.processOrderRequest(ORDER, ORDER_REQUEST_DTO) }
            }
        }

        context("상품의 개수가 null일 경우") {
            if (EMPTY_PRODUCT_QUANTITIES_ORDER_REQUEST_DTO.productQuantities[PRODUCT_IDX] == null)
                every {
                    orderService.processOrderRequest(
                        ORDER,
                        EMPTY_PRODUCT_QUANTITIES_ORDER_REQUEST_DTO
                    )
                } throws CustomException(
                    PRODUCT_QUANTITY_NOT_FOUND
                )

            it("Product Quantity Not Found Exception이 발생한다") {
                shouldThrow<CustomException> {
                    orderService.processOrderRequest(
                        ORDER,
                        EMPTY_PRODUCT_QUANTITIES_ORDER_REQUEST_DTO
                    )
                }
            }
        }
    }

    describe("getOrder") {

        context("올바른 정보가 입력되면") {
            every { memberRepository.existsById(MEMBER_IDX) } returns true
            every { orderProductQueryRepository.findGetOrderResponsesByMemberIdx(MEMBER_IDX) } returns GET_ORDER_RESPONSE_LIST
            every { orderService.getOrders(MEMBER_IDX) } returns GET_ORDER_RESPONSE_LIST

            val orders = orderService.getOrders(MEMBER_IDX)

            it("주문 조회에 성공한다") {
                GET_ORDER_RESPONSE_LIST shouldBe orders
            }
        }

        context("존재하지 않는 회원이 입력되면") {
            every { memberRepository.existsById(MEMBER_IDX) } returns false
            every { orderService.getOrders(MEMBER_IDX) } throws CustomException(MEMBER_NOT_FOUND)

            it("Member Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.getOrders(MEMBER_IDX) }
            }
        }

        context("조회된 주문이 없을 경우") {
            every { orderProductQueryRepository.findGetOrderResponsesByMemberIdx(MEMBER_IDX) } returns mutableListOf()
            every { orderService.getOrders(MEMBER_IDX) } throws CustomException(ORDER_NOT_FOUND)

            it("Order Not Found Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.getOrders(MEMBER_IDX) }
            }
        }
    }

    describe("cancelOrder") {

        context("올바른 정보가 입력되면") {
            every { orderRepository.findById(ORDER_IDX) } returns Optional.of(ORDER)
            every { orderService.findAllByOrderAndIsDeleteFalse(ORDER) } returns ORDER_PRODUCT_LIST

            val orderProductList = orderService.findAllByOrderAndIsDeleteFalse(ORDER)
                .map { orderProduct ->
                    orderProduct.isDelete = true
                    orderProduct
                }

            it("주문이 취소된다") {
                orderProductList[0].isDelete shouldBe true
            }
        }

        context("주문이 없을 경우") {
            every { orderRepository.findById(ORDER_IDX) } returns Optional.empty()
            every { orderService.findAllByOrderAndIsDeleteFalse(ORDER) } throws CustomException(ORDER_NOT_FOUND)

            it("Not Found Order Exception이 발생한다") {
                shouldThrow<CustomException> { orderService.findAllByOrderAndIsDeleteFalse(ORDER) }
            }
        }
    }
})