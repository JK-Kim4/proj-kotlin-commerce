package com.tutomato.commerce.domain.cart

import org.springframework.stereotype.Repository

@Repository
interface CartRepository {

    fun save(cart: Cart) : Cart

    fun save(cartItems: CartItem) : CartItem

    fun delete(cart: Cart)

    fun delete(cartItems: CartItem)

    fun findById(id: Long) : Cart?

    fun findByUserId(userId: Long) : Cart?

    fun findItemById(id: Long) : CartItem?


}