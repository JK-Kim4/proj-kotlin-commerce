package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import java.time.LocalDateTime

data class OrderCreatedEvent(
    val orderId: Long,
    val userId: Long,
    val amount: Money,
    val createdAt: LocalDateTime,
) {

    companion object {
        fun from(order: Order): OrderCreatedEvent {
            return OrderCreatedEvent(
                orderId = order.id,
                userId = order.userId,
                amount = order.orderAmounts.subTotal,
                createdAt = order.createdAt
            )
        }
    }
}