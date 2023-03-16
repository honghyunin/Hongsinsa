package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.product.GetProductFilterDto
import commerce.hosinsa.domain.dto.product.ProductOptionDto
import commerce.hosinsa.domain.dto.product.RegistrationProductDto
import commerce.hosinsa.domain.dto.product.UpdateProductDto
import commerce.hosinsa.domain.service.ProductService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService) {

    @PostMapping("/registration")
    fun registrationProduct(@RequestBody registrationProductDto: RegistrationProductDto) =
        productService.registrationProduct(registrationProductDto)

    @PostMapping("/option/add/{productIdx}")
    fun addProductOptions(@PathVariable productIdx: Int, @RequestBody newOptions: MutableList<ProductOptionDto>) =
        productService.addProductOption(productIdx, newOptions)

    @PutMapping("/update")
    fun updateProduct(@RequestBody updateProduct: UpdateProductDto) = productService.updateProduct(updateProduct)

    @PutMapping("/option/update/{productIdx}")
    fun updateProductOption(@PathVariable productIdx: Int, @RequestBody newOptions: MutableList<ProductOptionDto>) =
        productService.updateProductOption(productIdx, newOptions)

    @PutMapping("/soldOut/{productIdx}")
    fun updateIsSoldOut(@PathVariable productIdx: Int) = productService.updateIsSoldOut(productIdx)

    @GetMapping("/{productIdx}")
    fun getProduct(@PathVariable productIdx: Int) = productService.getProductResponse(productIdx)

    @GetMapping("/")
    fun getProducts(
        getProductFilterDto: GetProductFilterDto,
        @PageableDefault(size = 20) pageable: Pageable
    ) = productService.getProducts(getProductFilterDto, pageable)

    @DeleteMapping("/{productIdx}")
    fun deleteProduct(@PathVariable productIdx: Int) = productService.deleteProduct(productIdx)
}