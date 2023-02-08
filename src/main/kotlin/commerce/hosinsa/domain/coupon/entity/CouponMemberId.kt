package commerce.hosinsa.domain.coupon.entity

import java.io.Serializable
import javax.persistence.Column

class CouponMemberId(
    @Column(name = "member_id")
    var member: Int? = null,

    @Column(name = "coupon_member_id")
    var couponMemberId: Int? = null,
) : Serializable