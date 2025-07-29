package com.tutomato.commerce.interfaces.payment

data class PaymentRequest (val orderId: Long)

data class Token (val token: String)