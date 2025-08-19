package com.tutomato.commerce.domain.order

import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderRepository {

    fun findById(orderId: Long): Order?

    fun findByUserId(userId: Long): List<Order>

    fun findAll(): List<Order>

    fun findOrderLinesAll(): List<OrderLine>

    fun findPaidOrderBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Order>

    fun findOrderLinesByOrderIds(orderIds: Set<Long>): List<OrderLine>

    fun save(order: Order): Order

    fun deleteAll()
}