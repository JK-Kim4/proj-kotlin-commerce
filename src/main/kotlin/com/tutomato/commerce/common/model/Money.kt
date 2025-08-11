package com.tutomato.commerce.common.model

import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class Money(

    val value: BigDecimal

) {
    init {
        require(value >= BigDecimal.ZERO) { "금액은 0 이상이어야 합니다." }
    }

    companion object {
        val ZERO = Money(BigDecimal.ZERO)
    }

    operator fun plus(other: Money): Money = Money(value + other.value)

    operator fun minus(other: Money): Money = Money(value - other.value)

    operator fun times(quantity: Int): Money {
        require(quantity >= 0) { "수량은 0 이상이어야 합니다." }
        return Money(value.multiply(BigDecimal(quantity)))
    }

    fun isGreaterThan(other: Money): Boolean = value > other.value

}