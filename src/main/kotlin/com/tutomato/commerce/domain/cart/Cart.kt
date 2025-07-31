package com.tutomato.commerce.domain.cart

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Cart(
    @Id var id: Long = 0,
    val userId: Long,
    @Transient var cartItems: CartItems = CartItems(),
) {
    fun getItemByIds(productId : Long, optionId : Long) : CartItem? {
        return cartItems.getItemByIds(productId, optionId)
    }

    fun addItem(cartItem: CartItem) {
        this.cartItems = cartItems.addItem(cartItem)
    }
}