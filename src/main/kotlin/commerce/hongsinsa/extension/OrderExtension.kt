package commerce.hongsinsa.extension

import commerce.hongsinsa.dto.request.RequestDto
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.request.Request
import commerce.hongsinsa.entity.request.RequestStatus

fun RequestDto.toOrder(member: Member) = Request(
    address = this.address,
    recipient = this.name,
    phoneNumber = this.phoneNumber,
    message = this.deliveryInstructions,
    member = member,
    status = RequestStatus.Request_RECEIVED
)