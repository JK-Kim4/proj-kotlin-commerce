package com.tutomato.commerce.domain.order

import jakarta.persistence.NoResultException
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun save(command: OrderCommand.OrderSaveCommand): Order {
        return orderRepository.save(command.toEntity())
    }

    fun existsUnpaidOrderByUserId(userId: Long): Boolean {
        val orders = orderRepository.findByUserId(userId)

        return orders.any { it.orderStatus.isUnpaid() }
    }

    fun completeOrder(command: OrderCommand.CompleteOrder): Order {
        val order = orderRepository.findById(command.orderId)
            ?: throw NoResultException("Order with id ${command.orderId} not found")

        order.complete()

        return order
    }
}