package commerce.hongsinsa.cart

import commerce.hongsinsa.dto.cart.AddProductDto
import commerce.hongsinsa.dto.cart.GetCartDto
import commerce.hongsinsa.member.MEMBER
import commerce.hongsinsa.member.MEMBER_IDX
import commerce.hongsinsa.product.PRICE
import commerce.hongsinsa.product.PRODUCT
import commerce.hongsinsa.product.PRODUCT_IDX
import commerce.hongsinsa.product.PRODUCT_NAME
import commerce.hongsinsa.repository.cart.CartQueryRepository
import commerce.hongsinsa.repository.cart.CartRepository
import commerce.hongsinsa.repository.member.MemberRepository
import commerce.hongsinsa.repository.product.ProductRepository
import commerce.hongsinsa.service.cart.CartService
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
val cartQueryRepository = mockk<CartQueryRepository>()
val cartService = mockk<CartService>()