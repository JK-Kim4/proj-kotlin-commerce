package com.tutomato.commerce.common.model

import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class Money(

    val amount: BigDecimal

) {
    init {
        require(amount >= BigDecimal.ZERO) { "금액은 0 이상이어야 합니다." }
    }

    operator fun plus(other: Money): Money = Money(amount + other.amount)

    operator fun minus(other: Money): Money = Money(amount - other.amount)

    fun isGreaterThan(other: Money): Boolean = amount > other.amount

}