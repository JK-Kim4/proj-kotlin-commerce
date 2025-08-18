package com.tutomato.commerce.application.payment

import com.tutomato.commerce.domain.order.OrderCreatedEvent
import com.tutomato.commerce.domain.payment.PaymentCommand
import com.tutomato.commerce.domain.payment.PaymentService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PaymentOrderCreatedEventListener(
    private val paymentService: PaymentService,
) {

    @Async
    @EventListener
    fun createDefaultPayment(event: OrderCreatedEvent) {
        // 기본 결제 생성
        val command = PaymentCommand.Pay.of(
            orderId = event.orderId,
            userId = event.userId,
            amount = event.amount,
            method = null,
            type = null,
        )

        paymentService.save(command)
    }
}