package com.tutomato.commerce.interfaces.order

import org.springframework.http.ResponseEntity

interface OrderApiSpec {

    fun create(authorization: Token, orders: OrderLinesRequest): ResponseEntity<OrderResponse>

    fun cancel(authorization: Token, orderId: Long): ResponseEntity<OrderResponse>

}