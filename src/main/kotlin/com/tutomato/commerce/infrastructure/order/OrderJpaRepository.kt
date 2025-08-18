package com.tutomato.commerce.infrastructure.order

import com.tutomato.commerce.domain.order.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface OrderJpaRepository: JpaRepository<Order, Long> {

    fun findByUserId(userId: Long): List<Order>

    @Query(
        "select p " +
        "from Order p " +
        "where p.orderStatus = 'PAID' " +
        "and p.paidAt between :startDateTime and :endDateTime")
    fun findPaidOrderBetween(
        @Param("startDateTime") startDateTime: LocalDateTime,
        @Param("endDateTime") endDateTime: LocalDateTime): List<Order>
}