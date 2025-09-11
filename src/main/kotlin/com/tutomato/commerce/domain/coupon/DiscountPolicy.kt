package com.tutomato.commerce.domain.coupon

import com.tutomato.commerce.common.model.Money

abstract class DiscountPolicy {
    abstract fun calculateDiscountAmount(price: Money): Money
    abstract fun getDiscountAmount(price: Money): Money
}