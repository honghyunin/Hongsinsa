package commerce.hongsinsa.domain.service

import commerce.hongsinsa.domain.dto.cart.GetCartDto
import commerce.hongsinsa.domain.repository.cart.CartCustomRepository
import commerce.hongsinsa.domain.repository.cart.CartRepository
import commerce.hongsinsa.entity.cart.Cart
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.product.Product
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartCustomRepository: CartCustomRepository,
) {
    fun addProduct(member: Member, product: Product) {
        cartRepository.save(Cart(member = member, product = product))
    }

    fun getCart(memberIdx: Int): MutableList<GetCartDto> = cartCustomRepository.findProductsByMemberIdx(memberIdx)

    fun deleteCartProduct(productIdx: Int, memberIdx: Int) {
        val cart: Cart = cartRepository.findByProductIdxAndMemberIdxAndIsDeleteFalse(productIdx, memberIdx)
            ?: throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)

        cartRepository.delete(cart)
    }
}