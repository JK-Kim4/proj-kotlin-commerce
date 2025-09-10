package com.tutomato.commerce.domain.coupon

import com.tutomato.commerce.common.model.Money

class RateDiscountCoupon(
    private val discountRate: Double
): DiscountCoupon{

    override fun getDiscountAmount(price: Money): Money {
        TODO("Not yet implemented")
    }
}