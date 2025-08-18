package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class OrderAmounts(

    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "sub_total_amount"))
    )
    val subTotal: Money,
) {

    companion object {
        fun zero(): OrderAmounts = OrderAmounts(Money.ZERO)
    }

}