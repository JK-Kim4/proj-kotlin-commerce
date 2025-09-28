package com.tutomato.commerce.coupon.application.port.out

import com.tutomato.commerce.coupon.domain.Coupon
import org.springframework.stereotype.Repository

@Repository
interface LoadCouponPort {

    fun findByName(name: String): Coupon?
}