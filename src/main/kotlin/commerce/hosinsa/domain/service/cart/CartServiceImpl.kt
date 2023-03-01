package commerce.hosinsa.domain.service.cart

import commerce.hosinsa.domain.dto.cart.AddProductDto
import commerce.hosinsa.entity.cart.Cart
import commerce.hosinsa.repository.cart.CartRepository
import commerce.hosinsa.entity.member.Member
import commerce.hosinsa.repository.member.MemberRepository
import commerce.hosinsa.entity.product.Product
import commerce.hosinsa.repository.product.ProductRepository
import commerce.hosinsa.global.exception.CustomException
import commerce.hosinsa.global.exception.ErrorCode.MEMBER_NOT_FOUND
import commerce.hosinsa.global.exception.ErrorCode.PRODUCT_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
) : CartService {
    override fun addProduct(addProductDto: commerce.hosinsa.domain.dto.cart.AddProductDto) {
        val member: Member = memberRepository.findById(addProductDto.memberId)
                .orElseThrow { throw CustomException(MEMBER_NOT_FOUND) }
        val product: Product = productRepository.findByProductId(addProductDto.productId)
            ?: throw CustomException(PRODUCT_NOT_FOUND)

        cartRepository.save(Cart(member = member, product = product))
    }
}