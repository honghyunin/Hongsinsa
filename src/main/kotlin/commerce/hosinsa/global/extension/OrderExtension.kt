package commerce.hosinsa.global.extension

import commerce.hosinsa.domain.dto.order.OrderRequestDto
import commerce.hosinsa.entity.member.Member
import commerce.hosinsa.entity.order.Order

fun OrderRequestDto.toOrder(member: Member) = Order(
    address = this.address,
    recipient = this.name,
    phoneNumber = this.phoneNumber,
    requestedMessage = this.deliveryInstructions,
    member = member
)