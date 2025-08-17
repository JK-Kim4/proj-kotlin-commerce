package com.tutomato.commerce.domain.payment

data class PaymentPaidEvent(
    val paymentId: Long,
    val orderId: Long,
) {

}