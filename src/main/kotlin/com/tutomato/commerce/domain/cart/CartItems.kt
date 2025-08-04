package com.tutomato.commerce.domain.cart

class CartItems(

    val items : List<CartItem> = mutableListOf<CartItem>(),

) {
    fun getItemByIds(productId : Long, optionId : Long) : CartItem? {
        return items.firstOrNull { item -> item.productId == productId && item.optionId == optionId }
    }

    fun addItem(cartItem: CartItem) : CartItems {
        val newList = items + cartItem
        return CartItems(newList)
    }

    fun remove(cartItem: CartItem) : CartItems {
        val newList = items - cartItem
        return CartItems(newList)
    }

    fun exist(cartItem: CartItem) : Boolean {
        return items.any { it.productId == cartItem.productId && it.optionId == cartItem.optionId }
    }

    fun exist(cartItemId: Long) : Boolean {
        return items.any { it.id == cartItemId }
    }
}