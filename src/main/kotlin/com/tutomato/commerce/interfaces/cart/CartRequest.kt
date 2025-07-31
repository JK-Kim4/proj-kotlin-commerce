package com.tutomato.commerce.interfaces.cart

data class Token (val token: String)

data class CartItemsRequest (val items: List<CartItemRequest>)

data class CartItemRequest (val productId: Long, val optionId: Long, val quantity: Int)

data class CartItemUpdateRequest (val quantity: Int)