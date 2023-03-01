package commerce.hosinsa.domain.service.cart

import commerce.hosinsa.domain.dto.cart.AddProductDto

interface CartService {
    fun addProduct(addProductDto: AddProductDto)
}