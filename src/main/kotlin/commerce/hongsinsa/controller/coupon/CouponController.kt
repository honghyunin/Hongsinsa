package commerce.hongsinsa.controller.coupon

import commerce.hongsinsa.dto.coupon.SaveCouponDto
import commerce.hongsinsa.service.brand.BrandService
import commerce.hongsinsa.service.coupon.CouponService
import commerce.hongsinsa.extension.toCoupon
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/coupons")
class CouponController(
    private val couponService: CouponService,
    private val brandService: BrandService
) {

    @PostMapping("/save")
    fun saveCoupon(@RequestBody saveCouponDto: SaveCouponDto) {

        val coupon = saveCouponDto.let {
            if (it.brandName == null) it.toCoupon(null)
            else it.toCoupon(brandService.findBrandByName(it.brandName))
        }

        couponService.saveCoupon(coupon)
    }
}