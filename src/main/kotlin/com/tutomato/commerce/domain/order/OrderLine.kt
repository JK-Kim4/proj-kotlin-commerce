package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*

@Entity
@Table(name = "order_line")
class OrderLine(

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "productId", column = Column(name = "product_id")),
        AttributeOverride(name = "optionId", column = Column(name = "option_id")),
        AttributeOverride(name = "price", column = Column(name = "price")),
        AttributeOverride(name = "quantity", column = Column(name = "quantity")),
    )
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