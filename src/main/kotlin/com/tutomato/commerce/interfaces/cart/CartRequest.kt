package com.tutomato.commerce.interfaces.cart

import com.tutomato.commerce.domain.cart.CartCommand

data class Token (val token: String)

data class CartItemsRequest (val items: List<CartItemRequest>)

data class CartItemRequest (
    val productId: Long,
    val optionId: Long,
    val quantity: Int
) {
    fun toCommand(userId: Long) : CartCommand.SaveCartItem {
        return CartCommand.SaveCartItem(
            userId = userId,
            productId = productId,
            optionId = optionId,
            quantity = quantity
        )
    }
}

data class CartItemUpdateRequest (
    val quantity: Int
) {
    fun toCommand(cartItemId: Long) : CartCommand.UpdateItemAmount {
        return CartCommand.UpdateItemAmount(cartItemId, quantity)
    }
}