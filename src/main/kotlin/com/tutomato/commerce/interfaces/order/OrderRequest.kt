package com.tutomato.commerce.interfaces.order

class OrderRequest {

    class Token (val token: String)

    class OrderLines (val orderLines: List<OrderLine>)

    class OrderLine (val productId: Long, val quantity: Int)

    class Order (val orderId: Long)


}