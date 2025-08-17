package com.tutomato.commerce.domain.order

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
@Table(name = "orders")
class Order protected constructor(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var id: Long = 0,

    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    var orderStatus: OrderStatus = OrderStatus.CREATED,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "subTotal", column = Column(name = "sub_total_amount"))
    )
    var orderAmounts: OrderAmounts = OrderAmounts.zero(),
) {

    // JPA용 무인자 생성자 (필수). 외부 사용 금지.
    @Deprecated("JPA only", level = DeprecationLevel.ERROR)
    protected constructor() : this(
        id = 0L,
        userId = 0L,
        orderStatus = OrderStatus.CREATED,
        orderAmounts = OrderAmounts.zero(),
    )

    @Transient
    var orderLines: OrderLines = OrderLines(listOf())

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun calculate() {
        orderAmounts = OrderAmounts(orderLines.calculateSubTotal())
    }

    fun pending() {
        orderStatus = OrderStatus.PENDING
    }

    fun complete() {
        updatedAt = LocalDateTime.now()
        orderStatus = OrderStatus.PAID
    }

    companion object {
        // 정적 팩토리: 생성 시 라인 세팅 후 한번에 계산
        fun create(
            id: Long = 0,
            userId: Long,
            lines: List<OrderLine>,
            status: OrderStatus = OrderStatus.CREATED,
        ): Order {
            val order = Order(
                id = id,
                userId = userId,
                orderStatus = status,
            )

            order.orderLines = OrderLines(lines)
            order.calculate()

            return order
        }
    }

}