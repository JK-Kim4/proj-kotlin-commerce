package com.tutomato.commerce.interfaces.cart

class CartResponse {

    class Cart(val userId: Long, val items: List<Item>)

    class Item(val productId: Long, val quantity: Int)

}
