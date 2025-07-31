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
}