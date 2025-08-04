package com.tutomato.commerce.support.domain

import com.tutomato.commerce.domain.cart.Cart
import com.tutomato.commerce.domain.cart.CartItem
import com.tutomato.commerce.domain.cart.CartItems

object CartDomainSupport {

    fun fixture(
        id: Long = 0L,
        userId: Long,
        cartItems: List<CartItem> = listOf(),
    ) : Cart{
        return Cart(
            id = id,
            userId = userId,
            cartItems = CartItems(cartItems)
        )
    }

    fun itemFixture(
        id: Long = 0L,
        productId: Long,
        optionId: Long,
        quantity: Int,
        cart: Cart,
    ): CartItem {
        return CartItem(
            id = id,
            productId = productId,
            optionId = optionId,
            quantity = quantity,
            cart = cart,
        )
    }

}