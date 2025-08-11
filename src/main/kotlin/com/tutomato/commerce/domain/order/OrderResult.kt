package com.tutomato.commerce.domain.order

import java.math.BigDecimal

data class OrderSaveResult(
    val id: Long,
    val subTotal: BigDecimal,
    val orderStatus: String,
) {
    companion object {
        fun from(order: Order): OrderSaveResult {
            return OrderSaveResult(
                id = order.id,
                subTotal = order.orderAmounts.subTotal.value,
                orderStatus = order.orderStatus.name
            )
        }
    }
}