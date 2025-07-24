package com.tutomato.commerce.interfaces.order

import org.springframework.http.ResponseEntity

interface OrderApiSpec {

    fun create(authorization: OrderRequest.Token, orders: OrderRequest.OrderLines): ResponseEntity<OrderResponse.Order>

    fun cancel(authorization: OrderRequest.Token, orderId: Long): ResponseEntity<OrderResponse.Order>

}