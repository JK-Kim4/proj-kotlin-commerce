package com.tutomato.commerce.coupon.application.port.out

import com.tutomato.commerce.coupon.domain.Coupon
import org.springframework.stereotype.Repository

@Repository
interface LoadCouponPort {

    fun findById(couponId: Long): Coupon?
    fun findByName(name: String): Coupon?
    fun isAlreadyIssued(couponId: Long, userId: Long): Boolean
}