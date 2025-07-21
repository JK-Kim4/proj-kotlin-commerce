package com.tutomato.commerce.interfaces.cart

class CartRequest {

    class Token (val token: String)

    class Items (val items: List<Item>)

    class Item (val productId: Long, val quantity: Int)

}
