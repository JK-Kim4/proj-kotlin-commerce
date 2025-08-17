package com.tutomato.commerce.application.order

import com.tutomato.commerce.domain.order.OrderCommand
import com.tutomato.commerce.domain.order.OrderService
import com.tutomato.commerce.domain.payment.PaymentPaidEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class OrderPaymentPaidEventListener(
    private val orderService: OrderService
) {

    @Async
    @EventListener
    fun complete(event: PaymentPaidEvent) {
        orderService.completeOrder(OrderCommand.CompleteOrder(event.orderId))
    }
}