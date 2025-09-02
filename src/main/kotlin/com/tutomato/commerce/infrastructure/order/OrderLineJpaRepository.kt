package com.tutomato.commerce.infrastructure.order

import com.tutomato.commerce.domain.order.OrderLine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderLineJpaRepository: JpaRepository<OrderLine, Long> {

    @Query("select ol from OrderLine ol where ol.order.id in :orderIds")
    fun findOrderLinesByOrderIds(@Param("orderIds") orderIds: Set<Long>): List<OrderLine>

}