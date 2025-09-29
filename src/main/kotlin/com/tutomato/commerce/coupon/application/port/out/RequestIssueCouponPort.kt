package com.tutomato.commerce.coupon.application.port.out

import com.tutomato.commerce.coupon.application.dto.CouponResult
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface RequestIssueCouponPort {

    fun isAlreadyRequested(couponId: Long, userId: Long): Boolean
    fun request(couponId: Long, userId: Long, requestedAt: OffsetDateTime)
}