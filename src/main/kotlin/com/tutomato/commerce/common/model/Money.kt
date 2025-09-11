package com.tutomato.commerce.common.model

import jakarta.persistence.Embeddable
import java.math.BigDecimal
import java.math.RoundingMode

@Embeddable
data class Money(

    val value: BigDecimal

) {

    companion object {
        val ZERO = Money(BigDecimal.ZERO)
    }

    operator fun plus(other: Money): Money = Money(value + other.value)

    operator fun minus(other: Money): Money = Money(value - other.value)

    operator fun times(quantity: Int): Money {
        require(quantity >= 0) { "수량은 0 이상이어야 합니다." }
        return Money(value.multiply(BigDecimal(quantity)))
    }

    operator fun times(quantity: Double): Money {
        require(quantity >= 0) { "수량은 0 이상이어야 합니다." }
        return Money(value.multiply(BigDecimal(quantity)))
    }

    // BigDecimal 기반 연산만 허용
    operator fun times(multiplier: BigDecimal): Money {
        require(multiplier >= BigDecimal.ZERO) { "수량/비율은 0 이상이어야 합니다." }
        val result = value.multiply(multiplier).setScale(0, RoundingMode.HALF_UP)
        return Money(result)
    }

    fun isGreaterThan(other: Money): Boolean = value > other.value

    fun isGreaterThanOrEquals(other: Money): Boolean = value >= other.value

    fun coerceAtLeast(min: Money): Money =
        if (value < min.value) min else this

}