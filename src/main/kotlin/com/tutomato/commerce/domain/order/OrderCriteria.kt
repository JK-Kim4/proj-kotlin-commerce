package com.tutomato.commerce.domain.order

import java.time.LocalDateTime

class OrderCriteria {

    data class PaidOrders(
        val period: OrderPeriod,
        val calculatedAt: LocalDateTime,
    )
}
