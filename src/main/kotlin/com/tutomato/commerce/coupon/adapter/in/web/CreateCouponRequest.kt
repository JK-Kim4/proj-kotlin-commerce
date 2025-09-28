package com.tutomato.commerce.coupon.adapter.`in`.web

import com.tutomato.commerce.coupon.domain.CouponType
import java.math.BigDecimal
import java.time.LocalDate

data class CreateCouponRequest(
    val nane: String,
    val type: CouponType,
    val discountValue: BigDecimal,
    val amount: Int,
    val expireDate: LocalDate,
)