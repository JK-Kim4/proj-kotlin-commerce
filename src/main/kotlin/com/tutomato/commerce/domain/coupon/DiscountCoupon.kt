package com.tutomato.commerce.domain.coupon

import com.tutomato.commerce.common.model.Money

interface DiscountCoupon {

    fun getDiscountAmount(price: Money): Money
}