package com.tutomato.commerce.domain.cart

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class CartItem(
    @Id var id: Long = 0,
    val productId: Long,
    val optionId: Long,
    var quantity: Int,
    @ManyToOne(fetch = FetchType.LAZY) val cart : Cart,
) {

    fun updateQuantity(quantity: Int) {
        this.quantity = quantity
    }

}