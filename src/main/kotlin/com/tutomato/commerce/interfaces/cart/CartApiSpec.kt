package com.tutomato.commerce.interfaces.cart

import org.springframework.http.ResponseEntity

interface CartApiSpec {

    fun cart(authorization: Token) : ResponseEntity<CartResponse>

    fun addCart(authorization: Token, items: CartItemsRequest) : ResponseEntity<CartResponse>
}