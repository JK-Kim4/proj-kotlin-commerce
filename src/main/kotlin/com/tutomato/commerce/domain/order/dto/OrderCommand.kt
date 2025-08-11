package com.tutomato.commerce.domain.order.dto

import com.tutomato.commerce.common.model.Money
import com.tutomato.commerce.domain.order.Order
import com.tutomato.commerce.domain.order.OrderLine
import com.tutomato.commerce.domain.order.ProductSnapshot
import java.math.BigDecimal

data class OrderSave(
    val userId: Long,
    val snapshots: List<OrderProductSnapshot>,
) {
    fun toEntity(): Order {
        return Order.create(
            userId = userId,
            lines = snapshots.map { it.toOrderLineEntity() }.toList()
        )
    }
}

data class OrderProductSnapshot(
    val productId: Long,
    val optionId: Long,
    val price: BigDecimal,
    val quantity: Int,
) {
    fun toOrderLineEntity(): OrderLine {
        return OrderLine(
            ProductSnapshot(
                productId = productId,
                optionId = optionId,
                price = Money(price),
                quantity = quantity
            )
        )
    }
}