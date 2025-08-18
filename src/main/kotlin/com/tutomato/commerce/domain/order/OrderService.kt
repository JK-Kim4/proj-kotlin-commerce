package com.tutomato.commerce.domain.order

import jakarta.persistence.NoResultException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
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

    fun findPaidOrdersByCriteria(criteria: OrderCriteria.PaidOrders): List<Order> {
        val startDateTime = criteria.period.getStartDateTime(criteria.calculatedAt)

        return orderRepository.findPaidOrderBetween(startDateTime, criteria.calculatedAt)
    }
}