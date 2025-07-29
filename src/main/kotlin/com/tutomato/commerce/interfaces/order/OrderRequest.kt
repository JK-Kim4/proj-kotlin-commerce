package com.tutomato.commerce.interfaces.order

class Token (val token: String)

class OrderLinesRequest (val orderLines: List<OrderLineRequest>)

class OrderLineRequest (val productId: Long, val quantity: Int)

class OrderRequest (val orderId: Long)