package commerce.hongsinsa.controller.cart

import commerce.hongsinsa.dto.cart.AddProductDto
import commerce.hongsinsa.dto.cart.GetCartDto
import commerce.hongsinsa.service.cart.CartService
import commerce.hongsinsa.service.member.MemberService
import commerce.hongsinsa.service.product.ProductService
import commerce.hongsinsa.config.utils.CurrentMemberUtil
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/carts")
class CartController(
    private val cartService: CartService,
    private val memberService: MemberService,
    private val currentMemberUtil: CurrentMemberUtil,
    private val productService: ProductService
) {

    @PostMapping("/add")
    fun addProduct(@RequestBody addProductDto: AddProductDto): ResponseEntity<Any> {
        cartService.addProduct(
            member = memberService.getMember(addProductDto.memberIdx),
            product = productService.getProduct(addProductDto.productIdx)
        )

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @GetMapping("/{memberIdx}")
    fun getCart(@PathVariable memberIdx: Int): ResponseEntity<Any> {
        memberService.existsByIdx(memberIdx)
        val orders = cartService.getCart(memberIdx)

        return ResponseEntity<Any>(orders, HttpStatus.OK)
    }

    @DeleteMapping("/{productIdx}")
    fun deleteCartProduct(@PathVariable productIdx: Int): ResponseEntity<Any> {
        val memberIdx = currentMemberUtil.getCurrentMemberIfAuthenticated().idx!!
        val cart = cartService.findByProductIdxAndMemberIdxAndIsDeleteFalse(productIdx, memberIdx)
            ?: throw CustomException(ErrorCode.PRODUCT_NOT_FOUND)

        cartService.deleteCartProduct(cart)

        return ResponseEntity<Any>(HttpStatus.OK)
    }
}