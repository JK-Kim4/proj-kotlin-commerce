package com.tutomato.commerce.interfaces.payment

class PaymentResponse {

    class Payment(val paymentId: Long, val orderId: Long, val orderStatus: String)

}