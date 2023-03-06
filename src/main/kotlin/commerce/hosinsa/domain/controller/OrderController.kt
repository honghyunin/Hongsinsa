package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.order.OrderRequestDto
import commerce.hosinsa.domain.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/order")
class OrderController(private val orderService: OrderService) {

    @PostMapping()
    fun requestOrder(@RequestBody orderRequestDto: OrderRequestDto): Unit = orderService.orderRequest(orderRequestDto)

    @GetMapping("/{memberIdx}")
    fun getOrder(@PathVariable memberIdx: Int) = orderService.getOrder(memberIdx)

    @DeleteMapping("/cancel/{orderIdx}")
    fun cancelOrder(@PathVariable orderIdx: Int) = orderService.cancelOrder(orderIdx)
}