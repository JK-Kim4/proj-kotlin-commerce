package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import java.math.BigDecimal

class OrderCommand {

    data class OrderSaveCommand(
        val userId: Long,
        val snapshots: List<OrderProductSnapshot>,
    ) {
        fun toEntity(): Order {
            return Order.create(
                userId = userId,
                lines = snapshots.map { it.toOrderLineEntity() }.toList()
            )
        }

        fun getSnapshotOptionIds(): Set<Long> {
            return snapshots.map { it.optionId }.toSet()
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

    data class CompleteOrder(
        val orderId: Long,
    )
}