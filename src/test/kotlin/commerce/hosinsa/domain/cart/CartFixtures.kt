package commerce.hosinsa.domain.cart

import commerce.hosinsa.domain.dto.cart.AddProductDto
import commerce.hosinsa.domain.dto.cart.CartResponse
import commerce.hosinsa.domain.member.MEMBER
import commerce.hosinsa.domain.member.MEMBER_IDX
import commerce.hosinsa.domain.product.PRICE
import commerce.hosinsa.domain.product.PRODUCT
import commerce.hosinsa.domain.product.PRODUCT_IDX
import commerce.hosinsa.domain.product.PRODUCT_NAME
import commerce.hosinsa.domain.repository.CartCustomRepository
import commerce.hosinsa.domain.repository.CartRepository
import commerce.hosinsa.domain.repository.MemberRepository
import commerce.hosinsa.domain.repository.ProductRepository
import commerce.hosinsa.domain.service.CartService
import commerce.hosinsa.entity.cart.Cart
import io.mockk.mockk

const val QUANTITY: Int = 3
const val ORDER_PRICE: Int = 69500

val CART = Cart(
    MEMBER,
    PRODUCT
)

val CART_RESPONSE = CartResponse(
    PRODUCT_NAME,
    PRICE,
    QUANTITY,
    ORDER_PRICE
)

val CART_RESPONSE_LIST = mutableListOf(CART_RESPONSE, CART_RESPONSE)

val ADD_PRODUCT_DTO = AddProductDto(
    memberId = MEMBER_IDX,
    productId= PRODUCT_IDX
)

val cartRepository = mockk<CartRepository>()
val memberRepository = mockk<MemberRepository>()
val productRepository = mockk<ProductRepository>()
val cartCustomRepository = mockk<CartCustomRepository>()
val cartService = mockk<CartService>()