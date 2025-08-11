package com.tutomato.commerce.domain.order

import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun existsUnpaidOrderByUserId(userId: Long): Boolean {
        val orders = orderRepository.findByUserId(userId)

        return orders.any { it.orderStatus.isUnpaid() }
    }
}