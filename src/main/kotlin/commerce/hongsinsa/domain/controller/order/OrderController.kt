package commerce.hongsinsa.domain.controller.order

import commerce.hongsinsa.domain.dto.order.OrderRequestDto
import commerce.hongsinsa.domain.service.order.OrderService
import commerce.hongsinsa.global.config.utils.CurrentMemberUtil
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional

@RestController
@RequestMapping("/api/v1/order")
class OrderController(private val orderService: OrderService, private val currentMemberUtil: CurrentMemberUtil) {

    @Transactional
    @PostMapping()
    fun requestOrder(@RequestBody orderRequestDto: OrderRequestDto) {
        val order = orderService.saveOrder(orderRequestDto, currentMemberUtil.getCurrentMemberIfAuthenticated())
        orderService.processOrderRequest(order, orderRequestDto)
        orderService.decreaseStock(orderRequestDto.productQuantities)
    }

    @GetMapping("/{memberIdx}")
    fun getOrder(@PathVariable memberIdx: Int) = orderService.getOrder(memberIdx)

    @DeleteMapping("/cancel/{orderIdx}")
    fun cancelOrder(@PathVariable orderIdx: Int) = orderService.cancelOrder(orderIdx)
}