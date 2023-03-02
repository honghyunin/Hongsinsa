package commerce.hosinsa.domain.service.cart

import commerce.hosinsa.domain.dto.cart.AddProductDto
import commerce.hosinsa.domain.dto.cart.CartResponse

interface CartService {
    fun addProduct(addProductDto: AddProductDto)
    fun getCart(memberIdx: Int): MutableList<CartResponse>
    fun deleteCartProduct(productIdx: Int)
}