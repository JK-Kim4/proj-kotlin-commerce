package com.tutomato.commerce.infrastructure.order

import com.tutomato.commerce.domain.order.Order
import com.tutomato.commerce.domain.order.OrderRepository
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository,
    private val orderLineJpaRepository: OrderLineJpaRepository
): OrderRepository {

    override fun save(order: Order): Order {
        return orderJpaRepository.save(order)
            .also {
                val orderLine = order.orderLines
                orderLine.setOrder(it)
                orderLineJpaRepository.saveAll(orderLine.orderLines)
            }
    }

    override fun findByUserId(userId: Long): List<Order> {
        return orderJpaRepository.findByUserId(userId)
    }

    override fun findById(orderId: Long): Order? {
        return orderJpaRepository.findById(orderId).orElse(null)
    }
}