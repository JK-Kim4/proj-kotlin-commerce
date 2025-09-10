package com.tutomato.commerce.domain.coupon

import com.tutomato.commerce.common.model.Money

class AmountDiscountCoupon(
    private val discountAmount: Money,
): DiscountCoupon {

    override fun getDiscountAmount(price: Money): Money {
        TODO("Not yet implemented")
    }
}