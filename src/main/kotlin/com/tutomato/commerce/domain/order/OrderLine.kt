package com.tutomato.commerce.domain.order

import jakarta.persistence.*

@Entity
class OrderLine(
    @Id
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    val order: Order,

    @Embedded
    val snapshot: ProductSnapshot,
) {
}