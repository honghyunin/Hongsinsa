package commerce.hongsinsa.service.request

import commerce.hongsinsa.dto.request.GetRequestDto
import commerce.hongsinsa.dto.request.RequestDto
import commerce.hongsinsa.repository.request.RequestProductQueryRepository
import commerce.hongsinsa.repository.request.RequestProductRepository
import commerce.hongsinsa.repository.request.RequestQueryRepository
import commerce.hongsinsa.repository.request.RequestRepository
import commerce.hongsinsa.repository.product.ProductRepository
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.request.Request
import commerce.hongsinsa.entity.request.RequestProduct
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import commerce.hongsinsa.extension.toOrder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RequestService(
    private val requestRepository: RequestRepository,
    private val requestProductRepository: RequestProductRepository,
    private val requestProductQueryRepository: RequestProductQueryRepository,
    private val requestQueryRepository: RequestQueryRepository,
    private val productRepository: ProductRepository
) {

    @Transactional(rollbackFor = [Exception::class])
    fun saveRequest(requestDto: RequestDto, member: Member) =
        requestRepository.save(requestDto.toOrder(member))

    @Transactional(rollbackFor = [Exception::class])
    fun processRequest(request: Request, requestDto: RequestDto) {
        requestDto.productIdxList.forEach { productIdx ->
            val product = getProductIdx(productIdx)

            val quantity: Byte = requestDto.productQuantities[productIdx]
                ?: throw CustomException(ErrorCode.PRODUCT_QUANTITY_NOT_FOUND)

            requestProductRepository.save(
                RequestProduct(
                    product = product,
                    request = request,
                    count = quantity,
                    size = requestDto.size,
                    color = requestDto.color
                )
            )
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun decreaseStock(productQuantities: MutableMap<Int, Byte>) {
        productQuantities.forEach { (productIdx, quantity) ->
            val product = getProductIdx(productIdx)
            product.stock -= quantity
        }
    }

    @Transactional(readOnly = true)
    fun getRequests(memberIdx: Int): MutableList<GetRequestDto> {
        val orders = requestProductQueryRepository.findGetRequestResponsesByMemberIdx(memberIdx)

        if (orders.isEmpty())
            throw CustomException(ErrorCode.REQUEST_NOT_FOUND)

        return orders
    }

    fun findByIdxAndStatusOrderReceived(requestIdx: Int) =
        requestQueryRepository.findByIdxAndStatusOrderReceived(requestIdx)
            ?: throw CustomException(ErrorCode.REQUEST_NOT_FOUND)

    fun findAllByOrderAndIsDeleteFalse(request: Request) =
        requestProductRepository.findAllByRequestAndIsDeleteFalse(request)

    @Transactional(readOnly = true)
    fun getProductIdx(productIdx: Int) =
        productRepository.findByIdxAndIsDeleteFalse(productIdx)
            ?: throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)
}