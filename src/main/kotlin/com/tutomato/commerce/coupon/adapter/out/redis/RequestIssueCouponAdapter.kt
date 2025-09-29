package com.tutomato.commerce.coupon.adapter.out.redis

import com.tutomato.commerce.coupon.application.port.out.RequestIssueCouponPort
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class RequestIssueCouponAdapter(

): RequestIssueCouponPort {

    override fun isAlreadyRequested(couponId: Long, userId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(
        couponId: Long,
        userId: Long,
        requestedAt: OffsetDateTime
    ) {
        TODO("Not yet implemented")
    }
}