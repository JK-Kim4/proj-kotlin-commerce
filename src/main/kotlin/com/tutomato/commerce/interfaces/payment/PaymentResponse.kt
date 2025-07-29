package com.tutomato.commerce.interfaces.payment

data class PaymentResponse(val paymentId: Long, val orderId: Long, val orderStatus: String, val paymentDateTime: String)

