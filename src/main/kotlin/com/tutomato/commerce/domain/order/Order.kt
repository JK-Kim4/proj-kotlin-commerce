package com.tutomato.commerce.domain.order

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
class Order(

    @Id
    var id: Long = 0,

    @Embedded
    var orderAmounts: OrderAmounts? = null,


    val orderLines: OrderLines = OrderLines(),

    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus = OrderStatus.CREATED,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now(),
) {
}