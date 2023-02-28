package commerce.hosinsa.domain.cart.service

import commerce.hosinsa.domain.cart.dto.AddProductDto
import commerce.hosinsa.domain.cart.entity.Cart
import commerce.hosinsa.domain.cart.repository.CartRepository
import commerce.hosinsa.domain.member.entity.Member
import commerce.hosinsa.domain.member.repository.MemberRepository
import commerce.hosinsa.domain.product.entity.Product
import commerce.hosinsa.domain.product.repository.ProductRepository
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
    override fun addProduct(addProductDto: AddProductDto) {
        val member: Member = memberRepository.findById(addProductDto.memberId)
                .orElseThrow { throw CustomException(MEMBER_NOT_FOUND) }
        val product: Product = productRepository.findByProductId(addProductDto.productId)
            ?: throw CustomException(PRODUCT_NOT_FOUND)

        cartRepository.save(Cart(member = member, product = product))
    }
}