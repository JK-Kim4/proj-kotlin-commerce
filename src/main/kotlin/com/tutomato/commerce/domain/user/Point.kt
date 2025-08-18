package com.tutomato.commerce.domain.user

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Balance(
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "balance"))
    )
    val balance: Money = Money.ZERO,
) {

    fun deduct(amount: Money): Balance {
        if (!balance.isGreaterThanOrEquals(amount)) {
            throw IllegalArgumentException("BALANCE must be greater than or equal to balance")
        }

        return Balance(balance.minus(amount))
    }
}