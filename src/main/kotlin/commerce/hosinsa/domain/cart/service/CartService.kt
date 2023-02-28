package commerce.hosinsa.domain.cart.service

import commerce.hosinsa.domain.cart.dto.AddProductDto

interface CartService {
    fun addProduct(addProductDto: AddProductDto)
}