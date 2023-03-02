package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.cart.AddProductDto
import commerce.hosinsa.domain.dto.cart.CartResponse
import commerce.hosinsa.domain.service.cart.CartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/cart")
class CartController(private val cartService: CartService) {

    @PostMapping("/add")
    fun addProduct(@RequestBody addProductDto: AddProductDto): Unit =
        cartService.addProduct(addProductDto)

    @GetMapping("/{memberIdx}")
    fun getCart(@PathVariable memberIdx: Int): MutableList<CartResponse> = cartService.getCart(memberIdx)

    @DeleteMapping("/{productIdx}")
    fun deleteCartProduct(@PathVariable productIdx: Int): Unit = cartService.deleteCartProduct(productIdx)
}