package commerce.hongsinsa.controller.order

import commerce.hongsinsa.dto.order.OrderRequestDto
import commerce.hongsinsa.service.order.OrderService
import commerce.hongsinsa.config.utils.CurrentMemberUtil
import commerce.hongsinsa.entity.order.OrderStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(private val orderService: OrderService, private val currentMemberUtil: CurrentMemberUtil) {

    @PostMapping()
    fun requestOrder(@RequestBody orderRequestDto: OrderRequestDto): ResponseEntity<Any> {
        val order = orderService.saveOrder(orderRequestDto, currentMemberUtil.getCurrentMemberIfAuthenticated())
        orderService.processOrderRequest(order, orderRequestDto)
        orderService.decreaseStock(orderRequestDto.productQuantities)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @GetMapping("/{memberIdx}")
    fun getOrders(@PathVariable memberIdx: Int): ResponseEntity<Any> {
        val orders = orderService.getOrders(memberIdx)

        return ResponseEntity<Any>(orders, HttpStatus.OK)
    }

    @DeleteMapping("/{orderIdx}")
    fun cancelOrder(@PathVariable orderIdx: Int): ResponseEntity<Any> {
        val order = orderService.findByIdxAndStatusOrderReceived(orderIdx)
            .also { order -> order.status = OrderStatus.ORDER_CANCEL }

        orderService.findAllByOrderAndIsDeleteFalse(order)
            .forEach { orderProduct -> orderProduct.isDelete = true }

        return ResponseEntity<Any>(HttpStatus.OK)
    }
}