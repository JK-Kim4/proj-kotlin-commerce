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

    operator fun plus(other: Money): Money = Money(value + other.value)

    operator fun minus(other: Money): Money = Money(value - other.value)

    fun isGreaterThan(other: Money): Boolean = value > other.value

}