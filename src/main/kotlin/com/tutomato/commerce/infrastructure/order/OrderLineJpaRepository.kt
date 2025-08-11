package com.tutomato.commerce.infrastructure.order

import com.tutomato.commerce.domain.order.OrderLine
import org.springframework.data.jpa.repository.JpaRepository

interface OrderLineJpaRepository: JpaRepository<OrderLine, Long> {
}