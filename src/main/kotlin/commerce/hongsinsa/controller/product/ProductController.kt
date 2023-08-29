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
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService, private val brandService: BrandService)
    : ProductSwagger {

    @PostMapping("/")
    override fun registrationProduct(@RequestBody registrationProductDto: RegistrationProductDto): ResponseEntity<Any> {
        val brand = ifThrowIsNotFoundBrand(registrationProductDto.brandName)
        val product = registrationProductDto.toProduct(brand)

        productService.registrationProduct(product)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    private fun ifThrowIsNotFoundBrand(brandName: String?) =
        if (brandName == null) null
        else brandService.findBrandByName(brandName)

    @PostMapping("/option/{productIdx}")
    override fun addProductOptions(
        @PathVariable productIdx: Int,
        @RequestBody newOptions: MutableList<ProductOptionDto>
    ): ResponseEntity<Any> {
        val productOption = productService.addProductOption(productIdx, newOptions)

        return ResponseEntity.ok()
            .body(productOption)
    }

    @PutMapping("/")
    override fun updateProduct(@RequestBody updateProduct: UpdateProductDto): ResponseEntity<Any> {
        productService.updateProduct(updateProduct)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping("/option/{productIdx}")
    override fun updateProductOption(
        @PathVariable productIdx: Int,
        @RequestBody newOptions: MutableList<ProductOptionDto>
    ): ResponseEntity<Any> {
        productService.updateProductOption(productIdx, newOptions)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PutMapping("/sold-out/{productIdx}")
    override fun updateIsSoldOut(@PathVariable productIdx: Int): ResponseEntity<Any> {
        productService.updateIsSoldOut(productIdx)

        return ResponseEntity<Any>(HttpStatus.OK)
    }
    @GetMapping("/{productIdx}")
    override fun getProduct(@PathVariable productIdx: Int): ResponseEntity<Any> {
        val productResponse = productService.getProductResponse(productIdx)

        return ResponseEntity.ok().body(productResponse)
    }

    @GetMapping("/")
    override fun getProducts(
        getProductFilterDto: GetProductFilterDto,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Any> {
        val products = productService.getProducts(getProductFilterDto, pageable)

        return ResponseEntity.ok().body(products)
    }
    @GetMapping("/option/{productIdx}")
    override fun getProductOption(@PathVariable("productIdx") productIdx: Int): ResponseEntity<Any> {
        val productOption = productService.getProductOption(productIdx)
        val productOptionDto = productOption.map { ProductOptionDto(it) }.toMutableList()

        return ResponseEntity(productOptionDto, HttpStatus.OK)
    }

    @DeleteMapping("/{productIdx}")
    override fun deleteProduct(@PathVariable productIdx: Int): ResponseEntity<Any> {
        productService.deleteProduct(productIdx)

        return ResponseEntity<Any>(HttpStatus.OK)
    }
}