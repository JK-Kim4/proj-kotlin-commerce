package com.tutomato.commerce.coupon.domain

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

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

    @Column(name = "expire_date", nullable = false)
    val expireDate: LocalDate,
) {
    fun toPolicy(): DiscountPolicy =
        DiscountPolicyFactory.from(type, discountValue)

    fun getDiscountAmount(price: Money): Money =
        toPolicy().getDiscountAmount(price)

    fun calculateFinalPrice(price: Money): Money =
        toPolicy().calculateDiscountAmount(price)
            .coerceAtLeast(Money(BigDecimal.ZERO))
}