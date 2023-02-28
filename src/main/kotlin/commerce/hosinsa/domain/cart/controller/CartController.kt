package commerce.hosinsa.domain.cart.controller

import commerce.hosinsa.domain.cart.dto.AddProductDto
import commerce.hosinsa.domain.cart.service.CartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/cart")
class CartController(private val cartService: CartService) {

    @PostMapping("/add")
    fun addProduct(@RequestBody addProductDto: AddProductDto): Unit =
        cartService.addProduct(addProductDto)
}