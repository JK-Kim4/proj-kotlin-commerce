package com.tutomato.commerce.domain.cart

import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class CartItems(

    @OneToMany
    val items : List<CartItem> = mutableListOf<CartItem>(),

) {
    fun getItemByIds(productId : Long, optionId : Long) : CartItem? {
        return items.firstOrNull { item -> item.productId == productId && item.optionId == optionId }
    }
}