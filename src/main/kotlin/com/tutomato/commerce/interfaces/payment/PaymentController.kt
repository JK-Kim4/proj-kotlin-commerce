package com.tutomato.commerce.interfaces.payment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/payment")
class PaymentController : PaymentApiSpec {

    @PostMapping
    override fun payment(
        @RequestHeader("Authorization") authorization: Token,
        @RequestBody paymentRequest: PaymentRequest
    ): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(PaymentResponse(
            1L, paymentRequest.orderId, "PAID", LocalDateTime.of(2025, 1, 1, 1, 1).toString(),
        ))
    }

    @PostMapping("/{paymentId}/refund")
    override fun refund(
        @RequestHeader("Authorization") authorization: Token,
        @PathVariable paymentId: Long
    ): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(PaymentResponse(
            paymentId, 10L, "CANCELLED", LocalDateTime.of(2025, 1, 1, 1, 1).toString(),
        ))
    }
}