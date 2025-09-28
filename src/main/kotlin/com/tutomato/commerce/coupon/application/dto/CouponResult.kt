package com.tutomato.commerce.coupon.application.dto

class CouponResult {

    data class Issue(
        val userCouponId: Long,
        val couponId: Long,
        val userId: Long,
    )
}