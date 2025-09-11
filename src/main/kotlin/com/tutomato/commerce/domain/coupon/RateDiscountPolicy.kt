package com.tutomato.commerce.domain.coupon

import com.tutomato.commerce.common.model.Money
import java.math.BigDecimal

class RateDiscountPolicy(
    private val discountValue: BigDecimal
): DiscountPolicy() {

    override fun calculateDiscountAmount(price: Money): Money {
        return price.minus(getDiscountAmount(price))
    }

    override fun getDiscountAmount(price: Money): Money {
        return price.times(discountValue)
    }
}