package commerce.hongsinsa.domain.cart

import commerce.hongsinsa.domain.dto.cart.AddProductDto
import commerce.hongsinsa.domain.dto.cart.GetCartDto
import commerce.hongsinsa.domain.member.MEMBER
import commerce.hongsinsa.domain.member.MEMBER_IDX
import commerce.hongsinsa.domain.product.PRICE
import commerce.hongsinsa.domain.product.PRODUCT
import commerce.hongsinsa.domain.product.PRODUCT_IDX
import commerce.hongsinsa.domain.product.PRODUCT_NAME
import commerce.hongsinsa.domain.repository.cart.CartCustomRepository
import commerce.hongsinsa.domain.repository.cart.CartRepository
import commerce.hongsinsa.domain.repository.member.MemberRepository
import commerce.hongsinsa.domain.repository.product.ProductRepository
import commerce.hongsinsa.domain.service.CartService
import commerce.hongsinsa.entity.cart.Cart
import io.mockk.mockk

const val QUANTITY: Int = 3
const val ORDER_PRICE: Int = 69500

val CART = Cart(
    MEMBER,
    PRODUCT
)

val CART_RESPONSE = GetCartDto(
    PRODUCT_NAME,
    PRICE,
    QUANTITY,
    ORDER_PRICE
)

val CART_RESPONSE_LIST = mutableListOf(CART_RESPONSE, CART_RESPONSE)

val ADD_PRODUCT_DTO = AddProductDto(
    memberIdx = MEMBER_IDX,
    productIdx= PRODUCT_IDX
)

val cartRepository = mockk<CartRepository>()
val memberRepository = mockk<MemberRepository>()
val productRepository = mockk<ProductRepository>()
val cartCustomRepository = mockk<CartCustomRepository>()
val cartService = mockk<CartService>()