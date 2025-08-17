package com.tutomato.commerce.domain.payment

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Payment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    var id: Long = 0,
    val orderId: Long,
    val userId: Long,

    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "amount"))
    )
    val amount: Money,
    var method: PaymentMethod? = null,
    var type: PaymentType? = null,
    var paidAt: LocalDateTime? = null,
) {

    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun pay(method: PaymentMethod, type: PaymentType) {
        this.method = method
        this.type = type
        this.paidAt = LocalDateTime.now()
    }

}