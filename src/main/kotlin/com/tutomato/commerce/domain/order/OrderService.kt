package com.tutomato.commerce.domain.order

import com.tutomato.commerce.domain.order.dto.OrderSaveCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun save(command: OrderSaveCommand): Order {
        return orderRepository.save(command.toEntity())
    }

    @Transactional(readOnly = true)
    fun existsUnpaidOrderByUserId(userId: Long): Boolean {
        val orders = orderRepository.findByUserId(userId)

        return orders.any { it.orderStatus.isUnpaid() }
    }
}