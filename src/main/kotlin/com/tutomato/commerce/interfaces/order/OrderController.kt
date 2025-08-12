package com.tutomato.commerce.interfaces.order

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/orders")
class OrderController : OrderApiSpec {

    @PostMapping
    override fun create(
        @RequestHeader("Authorization") authorization: Token,
        @RequestBody orders: OrderLinesRequest
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(OrderResponse(
            1L, "PENDING", LocalDateTime.of(2025, 1, 1, 1, 1).toString()
        ))
    }

    @PostMapping("/{orderId}/cancel")
    override fun cancel(
        @RequestHeader("Authorization") authorization: Token,
        @PathVariable orderId: Long
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(OrderResponse(
            1L, "CANCELLED", LocalDateTime.of(2025, 1, 1, 1, 1).toString()
        ))
    }
}