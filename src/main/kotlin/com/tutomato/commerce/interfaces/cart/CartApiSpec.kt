package com.tutomato.commerce.interfaces.cart

import com.tutomato.commerce.domain.authentication.AuthenticatedUser
import org.springframework.http.ResponseEntity

interface CartApiSpec {

    fun cart(authorization: AuthenticatedUser) : ResponseEntity<CartResponse>

    fun addItem(authorization: AuthenticatedUser, item: CartItemRequest) : ResponseEntity<CartResponse>

    fun updateQuantity(authorization: AuthenticatedUser, id: Long, items: CartItemUpdateRequest) : ResponseEntity<Void>
}