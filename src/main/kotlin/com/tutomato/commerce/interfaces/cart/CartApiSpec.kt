package com.tutomato.commerce.interfaces.cart

import org.springframework.http.ResponseEntity

interface CartApiSpec {

    fun cart(authorization: CartRequest.Token) : ResponseEntity<CartResponse.Cart>

    fun addCart(authorization: CartRequest.Token, items: CartRequest.Items) : ResponseEntity<CartResponse.Cart>
}