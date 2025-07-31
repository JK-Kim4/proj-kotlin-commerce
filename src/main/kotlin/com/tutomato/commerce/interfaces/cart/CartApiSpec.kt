package com.tutomato.commerce.interfaces.cart

import org.springframework.http.ResponseEntity

interface CartApiSpec {

    fun cart(authorization: Token) : ResponseEntity<CartResponse>

    fun addItem(authorization: Token, items: CartItemsRequest) : ResponseEntity<CartResponse>

    fun updateQuantity(authorization: Token, id: Long, items: CartItemUpdateRequest) : ResponseEntity<CartItemUpdateResponse>
}