package com.tutomato.commerce.interfaces.payment

import org.springframework.http.ResponseEntity

interface PaymentApiSpec {

    fun payment(authorization: PaymentRequest.Token, paymentRequest: PaymentRequest) : ResponseEntity<PaymentResponse.Payment>

    fun cancel(authorization: PaymentRequest.Token, paymentId: Long) : ResponseEntity<PaymentResponse.Payment>

}