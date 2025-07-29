package com.tutomato.commerce.interfaces.payment

import org.springframework.http.ResponseEntity

interface PaymentApiSpec {

    fun payment(authorization: Token, paymentRequest: PaymentRequest) : ResponseEntity<PaymentResponse>

    fun refund(authorization: Token, paymentId: Long) : ResponseEntity<PaymentResponse>

}