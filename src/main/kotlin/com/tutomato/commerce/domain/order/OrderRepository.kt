package com.tutomato.commerce.domain.order

import org.springframework.stereotype.Repository

@Repository
interface OrderRepository {

    fun findByUserId(userId: Long): List<Order>

    fun save(order: Order): Order
}