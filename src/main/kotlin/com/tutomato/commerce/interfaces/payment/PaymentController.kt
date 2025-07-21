package com.tutomato.commerce.interfaces.payment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payment")
class PaymentController : PaymentApiSpec {

    @PostMapping
    override fun payment(
        @RequestHeader("Authorization") authorization: PaymentRequest.Token,
        @RequestBody paymentRequest: PaymentRequest
    ): ResponseEntity<PaymentResponse.Payment> {
        return ResponseEntity.ok(PaymentResponse.Payment(
            1L, paymentRequest.orderId, "PAID"
        ))
    }

    @PostMapping("/{paymentId}/cancel")
    override fun cancel(
        @RequestHeader("Authorization") authorization: PaymentRequest.Token,
        @PathVariable paymentId: Long
    ): ResponseEntity<PaymentResponse.Payment> {
        return ResponseEntity.ok(PaymentResponse.Payment(
            paymentId, 10L, "CANCELLED"
        ))
    }
}