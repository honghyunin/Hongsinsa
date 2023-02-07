package commerce.hosinsa.domain.product.controller

import commerce.hosinsa.domain.product.dto.GetProductFilterDto
import commerce.hosinsa.domain.product.dto.RegistrationProductDto
import commerce.hosinsa.domain.product.dto.UpdateProductDto
import commerce.hosinsa.domain.product.service.ProductService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService) {

    @PostMapping("/registration")
    fun registrationProduct(@RequestBody registrationProductDto: RegistrationProductDto) =
        productService.registrationProduct(registrationProductDto)

    @PutMapping("/update")
    fun updateProduct(@RequestBody updateProduct: UpdateProductDto) = productService.updateProduct(updateProduct)

    @PutMapping("/soldOut/{productId}")
    fun updateIsSoldOut(@PathVariable productId: Int) = productService.updateIsSoldOut(productId)

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: Int) = productService.getProduct(productId)

    @GetMapping("/")
    fun getProducts(
        getProductFilterDto: GetProductFilterDto,
        @PageableDefault(size = 20) pageable: Pageable
    ) = productService.getProducts(getProductFilterDto, pageable)
}