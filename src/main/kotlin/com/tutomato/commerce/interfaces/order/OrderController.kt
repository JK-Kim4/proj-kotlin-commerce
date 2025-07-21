package com.tutomato.commerce.interfaces.order

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/orders")
class OrderController : OrderApiSpec {

    @PostMapping
    override fun create(
        @RequestHeader("Authorization") authorization: OrderRequest.Token,
        @RequestBody orders: OrderRequest.OrderLines
    ): ResponseEntity<OrderResponse.Order> {
        return ResponseEntity.ok(OrderResponse.Order(
            1L, "PENDING", LocalDateTime.of(2025, 1, 1, 1, 1)
        ))
    }

    @PostMapping("/{orderId}/cancel")
    override fun cancel(
        @RequestHeader("Authorization") authorization: OrderRequest.Token,
        @PathVariable orderId: Long
    ): ResponseEntity<OrderResponse.Order> {
        return ResponseEntity.ok(OrderResponse.Order(
            1L, "CANCELLED", LocalDateTime.of(2025, 1, 1, 1, 1)
        ))
    }
}