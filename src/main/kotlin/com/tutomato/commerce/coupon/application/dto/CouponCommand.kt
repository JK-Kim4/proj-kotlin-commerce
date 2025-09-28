package com.tutomato.commerce.coupon.application.dto

import java.time.OffsetDateTime

class CouponCommand {

    data class Issue(
        val couponId: Long,
        val userId: Long,
        val requestedAt: OffsetDateTime,
    )

}