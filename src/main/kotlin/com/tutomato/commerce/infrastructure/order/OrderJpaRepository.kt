package com.tutomato.commerce.infrastructure.order

import com.tutomato.commerce.domain.order.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository: JpaRepository<Order, Long> {

    fun findByUserId(userId: Long): List<Order>
}