package com.tutomato.commerce.coupon.domain

import java.math.BigDecimal

enum class CouponType {
    RATE, AMOUNT
}

object DiscountPolicyFactory {
    fun from(type: CouponType, discountValue: BigDecimal): DiscountPolicy =
        when (type) {
            CouponType.RATE   -> RateDiscountPolicy(discountValue)          // e.g. "0.15"
            CouponType.AMOUNT -> AmountDiscountPolicy(discountValue)         // e.g. "1500"
        }
}