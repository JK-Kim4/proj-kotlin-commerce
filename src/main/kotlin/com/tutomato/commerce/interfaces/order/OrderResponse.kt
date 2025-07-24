package com.tutomato.commerce.interfaces.order

import java.time.LocalDateTime

class OrderResponse {

    class Order (val orderId : Long, val status: String, val updateDateTime: LocalDateTime)

}