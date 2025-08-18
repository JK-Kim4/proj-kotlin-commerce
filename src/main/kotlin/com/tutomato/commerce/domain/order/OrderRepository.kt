package com.tutomato.commerce.domain.order

import org.springframework.stereotype.Repository

@Repository
interface OrderRepository {

    fun findById(orderId: Long): Order?

    fun findByUserId(userId: Long): List<Order>

    fun findAll(): List<Order>

    fun findOrderLinesAll(): List<OrderLine>

    fun save(order: Order): Order

    fun deleteAll()
}