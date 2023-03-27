package commerce.hongsinsa.domain.controller.cart

import commerce.hongsinsa.domain.dto.cart.AddProductDto
import commerce.hongsinsa.domain.dto.cart.GetCartDto
import commerce.hongsinsa.domain.service.CartService
import commerce.hongsinsa.domain.service.MemberService
import commerce.hongsinsa.domain.service.ProductService
import commerce.hongsinsa.global.config.utils.CurrentMemberUtil
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
    fun getCart(@PathVariable memberIdx: Int): MutableList<GetCartDto> {
        memberService.existsByIdx(memberIdx)
        return cartService.getCart(memberIdx)
    }

    @DeleteMapping("/{productIdx}")
    fun deleteCartProduct(@PathVariable productIdx: Int): Unit =
        cartService.deleteCartProduct(productIdx, currentMemberUtil.getCurrentMemberIfAuthenticated().idx!!)
}