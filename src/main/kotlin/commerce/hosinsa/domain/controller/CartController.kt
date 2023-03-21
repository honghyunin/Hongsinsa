package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.cart.AddProductDto
import commerce.hosinsa.domain.dto.cart.CartResponse
import commerce.hosinsa.domain.service.CartService
import commerce.hosinsa.domain.service.MemberService
import commerce.hosinsa.domain.service.ProductService
import commerce.hosinsa.global.config.utils.CurrentMemberUtil
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/cart")
class CartController(
    private val cartService: CartService,
    private val memberService: MemberService,
    private val currentMemberUtil: CurrentMemberUtil,
    private val productService: ProductService
) {

    @PostMapping("/add")
    fun addProduct(@RequestBody addProductDto: AddProductDto) {
        cartService.addProduct(
            member = memberService.getMember(addProductDto.memberIdx),
            product = productService.getProduct(addProductDto.productIdx)
        )
    }

    @GetMapping("/{memberIdx}")
    fun getCart(@PathVariable memberIdx: Int): MutableList<CartResponse> {
        memberService.existsByIdx(memberIdx)
        return cartService.getCart(memberIdx)
    }

    @DeleteMapping("/{productIdx}")
    fun deleteCartProduct(@PathVariable productIdx: Int): Unit =
        cartService.deleteCartProduct(productIdx, currentMemberUtil.getCurrentMemberIfAuthenticated().idx!!)
}