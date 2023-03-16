package commerce.hosinsa.domain.service

import commerce.hosinsa.domain.dto.cart.AddProductDto
import commerce.hosinsa.domain.dto.cart.CartResponse
import commerce.hosinsa.domain.repository.CartCustomRepository
import commerce.hosinsa.domain.repository.CartRepository
import commerce.hosinsa.domain.repository.MemberRepository
import commerce.hosinsa.domain.repository.ProductRepository
import commerce.hosinsa.entity.cart.Cart
import commerce.hosinsa.entity.member.Member
import commerce.hosinsa.entity.product.Product
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode
import commerce.hosinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
    private val cartCustomRepository: CartCustomRepository,
) {
    fun addProduct(addProductDto: AddProductDto) {
        val member: Member = memberRepository.findById(addProductDto.memberId)
            .orElseThrow { throw CustomException(MEMBER_NOT_FOUND) }
        val product: Product = productRepository.findByIdxAndIsDeleteFalse(addProductDto.productId)
            ?: throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)

        cartRepository.save(Cart(member = member, product = product))
    }

    fun getCart(memberIdx: Int): MutableList<CartResponse> {

        if(!memberRepository.existsByIdxAndIsDeleteFalse(memberIdx))
            throw CustomException(MEMBER_NOT_FOUND)

        return cartCustomRepository.findProductsByMemberIdx(memberIdx)
    }

    fun deleteCartProduct(productIdx: Int) {
        val cart: Cart = cartRepository.findByProductIdx(productIdx)
            ?: throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)

        cartRepository.delete(cart)
    }
}