package com.tutomato.commerce.domain.payment

import java.math.BigDecimal

class PaymentResult {

    data class Payment(
        val paymentId: Long,
        val orderId: Long,
        val amount: BigDecimal,
    ) {
        companion object {
            fun from(payment: com.tutomato.commerce.domain.payment.Payment): Payment {
                return Payment(
                    paymentId = payment.id,
                    orderId = payment.orderId,
                    amount = payment.amount.value
                )
            }
        }
    }
}