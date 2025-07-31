package com.tutomato.commerce.infrastructure.cart

import com.tutomato.commerce.domain.cart.Cart
import com.tutomato.commerce.domain.cart.CartItem
import com.tutomato.commerce.domain.cart.CartItems
import com.tutomato.commerce.domain.cart.CartRepository
import org.springframework.stereotype.Repository

@Repository
class CartRepositoryImpl(
    private val cartRepository: CartJpaRepository,
    private val cartItemRepository: CartItemJpaRepository
) : CartRepository {

    override fun save(cart: Cart): Cart {
        return cartRepository.save(cart)
    }

    override fun save(cartItems: CartItem): CartItem {
        return cartItemRepository.save(cartItems)
    }

    override fun delete(cart: Cart) {
        cartRepository.delete(cart)
    }

    override fun delete(cartItems: CartItem) {
        cartItemRepository.delete(cartItems)
    }

    override fun findById(id: Long): Cart? {
        return cartRepository.findById(id).orElse(null)
            ?.also {
                val cartItems = cartItemRepository.findByCart(it)
                it.cartItems = CartItems(cartItems)
            }
    }

    override fun findByUserId(userId: Long): Cart? {
        return cartRepository.findByUserId(userId)
            ?.also {
                val cartItems = cartItemRepository.findByCart(it)
                it.cartItems = CartItems(cartItems)
            }
    }

    override fun findItemById(id: Long): CartItem {
        return cartItemRepository.findById(id).orElse(null)
    }
}