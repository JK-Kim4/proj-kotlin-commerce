package com.tutomato.commerce.coupon.application.dto

import com.tutomato.commerce.coupon.domain.Coupon
import com.tutomato.commerce.coupon.domain.CouponType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.OffsetDateTime

class CouponCommand {

    data class Create(
        val name: String,
        val type: CouponType,
        val discountValue: BigDecimal,
        val amount: Int,
        val expireDate: LocalDate,
    ) {
        init {
            require(amount > 0) { "Amount must be positive" }

            require(expireDate.isAfter(LocalDate.now())) { "Expiry date must be after date" }

            require(discountValue.compareTo(BigDecimal.ZERO) < 0) { "discountValue must be positive." }

            when (type) {
                CouponType.RATE -> {
                    require(discountValue <= BigDecimal.ONE) {
                        "discountValue(rate) must be less than or equal to 1.0."
                    }
                }
                CouponType.AMOUNT -> {
                    require(discountValue <= BigDecimal.valueOf(5_000_000)) {
                        "discountValue(amount) must be less than or equal to 5,000,000."
                    }
                }
            }
        }

        fun toEntity(): Coupon {
            return Coupon(
                name = name,
                type = type,
                discountValue = discountValue,
                amount = amount,
                expireDate = expireDate,
            )
        }
    }

    data class Issue(
        val couponId: Long,
        val userId: Long,
        val requestedAt: OffsetDateTime,
    )

}