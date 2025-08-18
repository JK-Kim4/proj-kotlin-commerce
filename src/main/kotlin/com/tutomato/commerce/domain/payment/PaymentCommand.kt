package com.tutomato.commerce.domain.payment

import com.tutomato.commerce.common.model.Money

class PaymentCommand {

    data class Pay(
        val orderId: Long,
        val userId: Long,
        val amount: Money,
        val method: PaymentMethod? = null,
        val type: PaymentType? = null,
    ) {
        companion object {
            fun of(orderId: Long, userId: Long, amount: Money, method: PaymentMethod?, type: PaymentType?): Pay {
                return Pay(
                    orderId = orderId,
                    userId = userId,
                    amount = amount,
                    method = method,
                    type = type,
                )
            }
        }

        fun toEntity(): Payment {
            return Payment(
                userId = userId,
                orderId = orderId,
                amount = amount,
                method = method,
            )
        }
    }
}