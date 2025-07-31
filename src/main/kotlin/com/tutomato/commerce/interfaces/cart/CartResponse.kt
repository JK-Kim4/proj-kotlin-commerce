package com.tutomato.commerce.interfaces.cart

import com.tutomato.commerce.domain.cart.Cart
import com.tutomato.commerce.domain.cart.CartItem

data class CartResponse (
    val userId: Long,
    val items: List<CartItemResponse>) {

    companion object {
        fun from(cart: Cart): CartResponse {
            return CartResponse(
                cart.userId,
                cart.cartItems.items.map { CartItemResponse.from(it) }
            )
        }
    }

}

data class CartItemResponse (
    val productId: Long,
    val optionId: Long,
    val quantity: Int,) {

    companion object {
        fun from(cartItem: CartItem): CartItemResponse {
            return CartItemResponse(
                cartItem.productId,
                cartItem.optionId,
                cartItem.quantity,
            )
        }
    }

}

data class CartItemUpdateResponse (
    val cartItemId: Long,
    val quantity: Int
) {
    companion object {
        fun from(cartItem: CartItem): CartItemUpdateResponse {
            return CartItemUpdateResponse(cartItem.productId, cartItem.quantity)
        }
    }
}