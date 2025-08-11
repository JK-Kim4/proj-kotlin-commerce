package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*

@Entity
class OrderLine(
    @Embedded
    val snapshot: ProductSnapshot,
) {
    init {
        calculatePrice()
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    lateinit var order: Order

    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "price"))
    )
    lateinit var price: Money

    fun calculatePrice() {
        price = snapshot.price * snapshot.quantity
    }
}