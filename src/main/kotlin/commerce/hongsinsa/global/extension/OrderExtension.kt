package commerce.hongsinsa.global.extension

import commerce.hongsinsa.domain.dto.order.OrderRequestDto
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.order.Order
import commerce.hongsinsa.entity.order.OrderStatus

fun OrderRequestDto.toOrder(member: Member) = Order(
    address = this.address,
    recipient = this.name,
    phoneNumber = this.phoneNumber,
    requestedMessage = this.deliveryInstructions,
    member = member,
    status = OrderStatus.ORDER_RECEIVED
)