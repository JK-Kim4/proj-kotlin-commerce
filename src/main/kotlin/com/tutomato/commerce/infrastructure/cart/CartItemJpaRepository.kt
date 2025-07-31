package com.tutomato.commerce.infrastructure.cart

import com.tutomato.commerce.domain.cart.Cart
import com.tutomato.commerce.domain.cart.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemJpaRepository : JpaRepository<CartItem, Long> {

    fun findByCart(cart: Cart) : List<CartItem>
}