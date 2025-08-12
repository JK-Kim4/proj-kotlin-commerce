package com.tutomato.commerce.domain.order

import java.time.LocalDateTime

data class OrderCreatedEvent(
    val orderId: Long,
    val createdAt: LocalDateTime,
) {

    companion object {
        fun from(order: Order): OrderCreatedEvent {
            return OrderCreatedEvent(
                orderId = order.id,
                createdAt = order.createdAt
            )
        }
    }
}