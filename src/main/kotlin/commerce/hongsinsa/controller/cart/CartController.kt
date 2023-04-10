package commerce.hongsinsa.controller.cart

import commerce.hongsinsa.dto.cart.AddProductDto
import commerce.hongsinsa.dto.cart.GetCartDto
import commerce.hongsinsa.service.cart.CartService
import commerce.hongsinsa.service.member.MemberService
import commerce.hongsinsa.service.product.ProductService
import commerce.hongsinsa.config.utils.CurrentMemberUtil
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