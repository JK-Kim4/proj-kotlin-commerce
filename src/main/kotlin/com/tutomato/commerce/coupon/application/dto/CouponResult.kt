package com.tutomato.commerce.coupon.application.dto

import com.tutomato.commerce.coupon.domain.Coupon
import com.tutomato.commerce.coupon.domain.CouponType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.OffsetDateTime

class CouponResult {

    data class Create(
        val id: Long,
        val name: String,
        val type: CouponType,
        val discountValue: BigDecimal,
        val amount: Int,
        val expiredAt: OffsetDateTime,
    ) {
        companion object {
            fun from(coupon: Coupon): Create {
                return Create(
                    id = coupon.id,
                    name = coupon.name,
                    type = coupon.type,
                    discountValue = coupon.discountValue,
                    amount = coupon.amount,
                    expiredAt = coupon.expiredAt,
                )
            }
        }
    }

    data class Issue(
        val userCouponId: Long,
        val couponId: Long,
        val userId: Long,
    )
}