package com.tutomato.commerce.coupon.domain

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.OffsetDateTime

@Entity
class Coupon(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0,

    @Column(name = "name",nullable = false, length = 120)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type", nullable = false, length = 16)
    val type: CouponType,

    @Column(name = "discount_value", precision = 19, scale = 4, nullable = false)
    val discountValue: BigDecimal,

    @Column(name = "amount", nullable = false)
    var amount: Int = 0,

    @Column(name = "expired_at", nullable = false)
    val expiredAt: OffsetDateTime,
) {
    companion object {
        fun create(
            name: String,
            type: CouponType,
            discountValue: BigDecimal,
            amount: Int,
            expiredAt: OffsetDateTime
        ): Coupon {
            return Coupon(
                name = name,
                type = type,
                discountValue = discountValue,
                amount = amount,
                expiredAt = expiredAt
            )
        }
    }

    fun toPolicy(): DiscountPolicy =
        DiscountPolicyFactory.from(type, discountValue)

    fun getDiscountAmount(price: Money): Money =
        toPolicy().getDiscountAmount(price)

    fun calculateFinalPrice(price: Money): Money =
        toPolicy().calculateDiscountAmount(price)
            .coerceAtLeast(Money(BigDecimal.ZERO))

    fun issuableValidation(requestedAt: OffsetDateTime) {
        if(requestedAt.isAfter(expiredAt)) {
            throw IllegalStateException("this coupon is already expired")
        }
    }
}