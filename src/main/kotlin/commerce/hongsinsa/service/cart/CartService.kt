package commerce.hongsinsa.service.cart

import commerce.hongsinsa.dto.cart.GetCartDto
import commerce.hongsinsa.repository.cart.CartQueryRepository
import commerce.hongsinsa.repository.cart.CartRepository
import commerce.hongsinsa.entity.cart.Cart
import commerce.hongsinsa.entity.member.Member
import commerce.hongsinsa.entity.product.Product
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
    fun deleteCartProduct(cart: Cart) {
        cartRepository.delete(cart)
    }

    @Transactional(readOnly = true)
    fun findByProductIdxAndMemberIdxAndIsDeleteFalse(productIdx: Int, memberIdx: Int) =
        cartRepository.findByProductIdxAndMemberIdxAndIsDeleteFalse(productIdx, memberIdx)
}