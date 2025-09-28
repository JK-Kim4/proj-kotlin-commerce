package com.tutomato.commerce.coupon.domain

import com.tutomato.commerce.common.model.Money

abstract class DiscountPolicy {
    abstract fun calculateDiscountAmount(price: Money): Money
    abstract fun getDiscountAmount(price: Money): Money
}