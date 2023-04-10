package commerce.hongsinsa.extension

import commerce.hongsinsa.dto.order.OrderRequestDto
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