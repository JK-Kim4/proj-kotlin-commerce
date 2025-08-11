package com.tutomato.commerce.domain.cart

import jakarta.persistence.*

@Entity
class CartItem(
    @Id var id: Long = 0,
    val productId: Long,
    val optionId: Long,
    var quantity: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "cart_id",
        foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    val cart : Cart,
) {

    fun updateQuantity(quantity: Int) {
        this.quantity = quantity
    }

}