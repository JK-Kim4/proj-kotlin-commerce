package com.tutomato.commerce.domain.order

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
class Order(

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private val _orderLines: List<OrderLine> = listOf(),
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var id: Long = 0

    @Embedded
    var orderAmounts: OrderAmounts? = null

    @Transient
    val orderLines: OrderLines = OrderLines(_orderLines)

    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus = OrderStatus.CREATED

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now()

}