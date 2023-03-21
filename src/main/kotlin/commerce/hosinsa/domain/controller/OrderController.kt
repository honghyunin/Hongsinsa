package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.order.OrderRequestDto
import commerce.hosinsa.domain.service.OrderService
import commerce.hosinsa.global.config.utils.CurrentMemberUtil
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
    }

    @GetMapping("/{memberIdx}")
    fun getOrder(@PathVariable memberIdx: Int) = orderService.getOrder(memberIdx)

    @DeleteMapping("/cancel/{orderIdx}")
    fun cancelOrder(@PathVariable orderIdx: Int) = orderService.cancelOrder(orderIdx)
}