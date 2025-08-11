package com.tutomato.commerce.domain.order

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
class Order(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var id: Long = 0,

    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus = OrderStatus.CREATED,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private val _orderLines: List<OrderLine> = listOf(),
) {

    @Embedded
    var orderAmounts: OrderAmounts? = null

    @Transient
    val orderLines: OrderLines = OrderLines(_orderLines)

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now()

}