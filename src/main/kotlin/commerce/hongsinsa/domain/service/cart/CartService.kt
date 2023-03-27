package commerce.hongsinsa.domain.service.cart

import commerce.hongsinsa.domain.dto.cart.GetCartDto
import commerce.hongsinsa.domain.repository.cart.CartQueryRepository
import commerce.hongsinsa.domain.repository.cart.CartRepository
import commerce.hongsinsa.entity.cart.Cart
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartQueryRepository: CartQueryRepository,
) {

    @Transactional(rollbackFor = [Exception::class])
    fun addProduct(member: Member, product: Product) {
        cartRepository.save(Cart(member = member, product = product))
    }

    @Transactional(readOnly = true)
    fun getCart(memberIdx: Int): MutableList<GetCartDto> = cartQueryRepository.findProductsByMemberIdx(memberIdx)

    @Transactional(rollbackFor = [Exception::class])
    fun deleteCartProduct(productIdx: Int, memberIdx: Int) {
        cartRepository.findByProductIdxAndMemberIdxAndIsDeleteFalse(productIdx, memberIdx)
            ?.let { cart -> cartRepository.delete(cart) }
            ?: throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)
    }
}