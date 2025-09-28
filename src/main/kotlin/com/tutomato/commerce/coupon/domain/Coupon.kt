package com.tutomato.commerce.coupon.domain

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

@Entity
class Coupon(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type", nullable = false, length = 16)
    val type: CouponType,

    @Column(name = "discount_value", precision = 19, scale = 4, nullable = false)
    val discountValue: BigDecimal,

    var amount: Int = 0,

    val expiredAt: Instant
) {
    fun toPolicy(): DiscountPolicy =
        DiscountPolicyFactory.from(type, discountValue)

    fun getDiscountAmount(price: Money): Money =
        toPolicy().getDiscountAmount(price)

    fun calculateFinalPrice(price: Money): Money =
        toPolicy().calculateDiscountAmount(price)
            .coerceAtLeast(Money(BigDecimal.ZERO))
}