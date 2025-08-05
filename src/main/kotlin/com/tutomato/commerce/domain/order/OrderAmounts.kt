package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.Embeddable

@Embeddable
data class OrderAmounts(
    val subTotal: Money,
    val discountAmount: Money,
    val grandTotal: Money,
) {
}