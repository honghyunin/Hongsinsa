package commerce.hongsinsa.controller.request

import commerce.hongsinsa.dto.request.RequestDto
import commerce.hongsinsa.service.request.RequestService
import commerce.hongsinsa.config.utils.CurrentMemberUtil
import commerce.hongsinsa.entity.request.RequestStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/request")
class RequestController(private val requestService: RequestService, private val currentMemberUtil: CurrentMemberUtil)
    : RequestSwagger {

    @PostMapping()
    override fun request(@RequestBody requestDto: RequestDto): ResponseEntity<Any> {
        val order = requestService.saveRequest(requestDto, currentMemberUtil.getCurrentMemberIfAuthenticated())
        requestService.processRequest(order, requestDto)
        requestService.decreaseStock(requestDto.productQuantities)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @GetMapping("/{memberIdx}")
    override fun getRequest(@PathVariable memberIdx: Int): ResponseEntity<Any> {
        val request = requestService.getRequests(memberIdx)

        return ResponseEntity<Any>(request, HttpStatus.OK)
    }

    @DeleteMapping("/{requestIdx}")
    override fun cancelRequest(@PathVariable requestIdx: Int): ResponseEntity<Any> {
        val request = requestService.findByIdxAndStatusOrderReceived(requestIdx)
            .also { request -> request.status = RequestStatus.Request_CANCEL }

        requestService.findAllByOrderAndIsDeleteFalse(request)
            .forEach { requestProduct -> requestProduct.isDelete = true }

        return ResponseEntity<Any>(HttpStatus.OK)
    }
}