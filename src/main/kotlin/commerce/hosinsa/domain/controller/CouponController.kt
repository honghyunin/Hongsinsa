package commerce.hosinsa.domain.controller

import commerce.hosinsa.domain.dto.coupon.SaveCouponDto
import commerce.hosinsa.domain.service.CouponService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/coupon")
class CouponController(private val couponService: CouponService) {

    @PostMapping("/save")
    fun saveCoupon(@RequestBody saveCouponDto: SaveCouponDto) = couponService.saveCoupon(saveCouponDto)
}