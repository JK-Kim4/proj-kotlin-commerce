package com.tutomato.commerce.interfaces.payment

class PaymentRequest (val orderId: Long) {

    class Token (val token: String)

}
