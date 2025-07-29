package com.tutomato.commerce.interfaces.cart

data class Token (val token: String)

data class CartItemsRequest (val items: List<CartItemRequest>)

data class CartItemRequest (val productId: Long, val quantity: Int)
