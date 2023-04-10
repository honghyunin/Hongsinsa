package commerce.hongsinsa.controller.product

import commerce.hongsinsa.dto.product.GetProductFilterDto
import commerce.hongsinsa.dto.product.ProductOptionDto
import commerce.hongsinsa.dto.product.RegistrationProductDto
import commerce.hongsinsa.dto.product.UpdateProductDto
import commerce.hongsinsa.service.brand.BrandService
import commerce.hongsinsa.service.product.ProductService
import commerce.hongsinsa.extension.toProduct
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService, private val brandService: BrandService) {

    @PostMapping("/registration")
    fun registrationProduct(@RequestBody registrationProductDto: RegistrationProductDto) {
        val product = registrationProductDto.toProduct(brandService.findBrandByName(registrationProductDto.brandName))

        productService.registrationProduct(product)
    }

    @PostMapping("/option/add/{productIdx}")
    fun addProductOptions(@PathVariable productIdx: Int, @RequestBody newOptions: MutableList<ProductOptionDto>) =
        productService.addProductOption(productIdx, newOptions)

    @PutMapping("/update")
    fun updateProduct(@RequestBody updateProduct: UpdateProductDto): Unit = productService.updateProduct(updateProduct)

    @PutMapping("/option/update/{productIdx}")
    fun updateProductOption(@PathVariable productIdx: Int, @RequestBody newOptions: MutableList<ProductOptionDto>) =
        productService.updateProductOption(productIdx, newOptions)

    @PutMapping("/soldOut/{productIdx}")
    fun updateIsSoldOut(@PathVariable productIdx: Int): Unit = productService.updateIsSoldOut(productIdx)

    @GetMapping("/{productIdx}")
    fun getProduct(@PathVariable productIdx: Int) = productService.getProductResponse(productIdx)

    @GetMapping("/")
    fun getProducts(
        getProductFilterDto: GetProductFilterDto,
        @PageableDefault(size = 20) pageable: Pageable
    ) = productService.getProducts(getProductFilterDto, pageable)

    @DeleteMapping("/{productIdx}")
    fun deleteProduct(@PathVariable productIdx: Int): Unit = productService.deleteProduct(productIdx)
}