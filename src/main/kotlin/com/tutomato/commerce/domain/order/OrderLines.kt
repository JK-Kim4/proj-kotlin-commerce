package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.Embeddable

@Embeddable
class OrderLines(
    val orderLines: List<OrderLine>
) {

    fun size(): Int {
        return orderLines.size
    }

    fun calculateSubTotal(): Money {
        return orderLines.fold(Money.ZERO) { acc, orderLine -> acc + orderLine.price }
    }

}