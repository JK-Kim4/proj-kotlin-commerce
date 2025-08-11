package com.tutomato.commerce.support.domain

import com.tutomato.commerce.common.model.Money
import com.tutomato.commerce.domain.order.Order
import com.tutomato.commerce.domain.order.OrderLine
import com.tutomato.commerce.domain.order.OrderStatus
import com.tutomato.commerce.domain.order.ProductSnapshot
import java.math.BigDecimal

object OrderDomainSupport {

    fun fixture(
        id: Long = 0L,
        orderStatus: OrderStatus = OrderStatus.CREATED,
        orderLines: List<OrderLine> = defaultOrderLines,
    ): Order {
        return Order(
            id = id,
            orderStatus = orderStatus,
            _orderLines = orderLines,
        )
    }

    fun fixtureOrderLine(
        productId: Long = 0L,
        optionId: Long = 0L,
        price: Money = Money(BigDecimal.valueOf(5000)),
        quantity: Int = 1,
    ): OrderLine {
        return OrderLine(
            ProductSnapshot(
                productId = productId,
                optionId = optionId,
                price = price,
                quantity = quantity
            )
        )
    }

    val defaultOrderLines = listOf(
        OrderLine(
            ProductSnapshot(
                productId = 1L,
                optionId = 2L,
                price = Money(BigDecimal.valueOf(50000)),
                quantity = 1
            )
        ),
        OrderLine(
            ProductSnapshot(
                productId = 1L,
                optionId = 3L,
                price = Money(BigDecimal.valueOf(50000)),
                quantity = 2
            )
        ),
        OrderLine(
            ProductSnapshot(
                productId = 1L,
                optionId = 4L,
                price = Money(BigDecimal.valueOf(50000)),
                quantity = 3
            )
        ),
    )
}