package com.tutomato.commerce.interfaces.cart

data class CartResponse (val userId: Long, val items: List<CartItemResponse>)

data class CartItemResponse (val productId: Long, val quantity: Int)

data class CartItemUpdateResponse (val cartItemId: Long, val quantity: Int)