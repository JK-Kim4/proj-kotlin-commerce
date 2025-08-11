package com.tutomato.commerce.domain.user

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.Embeddable

@Embeddable
data class Balance(
    val balance: Money = Money.ZERO,
) {
}