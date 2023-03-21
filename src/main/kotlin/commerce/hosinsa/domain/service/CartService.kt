package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.cart.CartResponse
import commerce.hosinsa.domain.repository.CartCustomRepository
import commerce.hosinsa.domain.repository.CartRepository
import commerce.hosinsa.entity.cart.Cart
import commerce.hosinsa.entity.member.Member
import commerce.hosinsa.entity.product.Product
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartCustomRepository: CartCustomRepository,
) {
    fun addProduct(member: Member, product: Product) {
        cartRepository.save(Cart(member = member, product = product))
    }

    fun getCart(memberIdx: Int): MutableList<CartResponse> {
        return cartCustomRepository.findProductsByMemberIdx(memberIdx)
    }

    fun deleteCartProduct(productIdx: Int, memberIdx: Int) {
        val cart: Cart = cartRepository.findByProductIdxAndMemberIdxAndIsDeleteFalse(productIdx, memberIdx)
            ?: throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)

        cartRepository.delete(cart)
    }
}