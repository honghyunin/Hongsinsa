package commerce.hongsinsa.controller.product

import commerce.hongsinsa.dto.product.GetProductFilterDto
import commerce.hongsinsa.dto.product.ProductOptionDto
import commerce.hongsinsa.dto.product.RegistrationProductDto
import commerce.hongsinsa.dto.product.UpdateProductDto
import commerce.hongsinsa.service.brand.BrandService
import commerce.hongsinsa.extension.toProduct
import commerce.hongsinsa.service.product.ProductService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService, private val brandService: BrandService) {

    @PostMapping("/")
    fun registrationProduct(@RequestBody registrationProductDto: RegistrationProductDto) {
        val brand = ifThrowIsNotFoundBrand(registrationProductDto.brandName)
        val product = registrationProductDto.toProduct(brand)

        productService.registrationProduct(product)
    }

    private fun ifThrowIsNotFoundBrand(brandName: String?) =
        if (brandName == null) null
        else brandService.findBrandByName(brandName)

    @PostMapping("/option/{productIdx}")
    fun addProductOptions(@PathVariable productIdx: Int, @RequestBody newOptions: MutableList<ProductOptionDto>) =
        productService.addProductOption(productIdx, newOptions)

    @PutMapping("/")
    fun updateProduct(@RequestBody updateProduct: UpdateProductDto): Unit = productService.updateProduct(updateProduct)

    @PutMapping("/option/{productIdx}")
    fun updateProductOption(@PathVariable productIdx: Int, @RequestBody newOptions: MutableList<ProductOptionDto>) =
        productService.updateProductOption(productIdx, newOptions)

    @PutMapping("/sold-out/{productIdx}")
    fun updateIsSoldOut(@PathVariable productIdx: Int): Unit = productService.updateIsSoldOut(productIdx)

    @GetMapping("/{productIdx}")
    fun getProduct(@PathVariable productIdx: Int) = productService.getProductResponse(productIdx)

    @GetMapping("/")
    fun getProducts(
        getProductFilterDto: GetProductFilterDto,
        @PageableDefault(size = 20) pageable: Pageable
    ) = productService.getProducts(getProductFilterDto, pageable)

    @GetMapping("/option/{productIdx}")
    fun getProductOption(@PathVariable("productIdx") productIdx: Int): ResponseEntity<*> {
        val productOption = productService.getProductOption(productIdx)
        val productOptionDto = productOption.map { ProductOptionDto(it) }.toMutableList()

        return ResponseEntity(productOptionDto, HttpStatus.OK)
    }

    @DeleteMapping("/{productIdx}")
    fun deleteProduct(@PathVariable productIdx: Int): Unit = productService.deleteProduct(productIdx)
}