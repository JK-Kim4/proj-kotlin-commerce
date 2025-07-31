package com.tutomato.commerce.application.cart

import com.tutomato.commerce.common.event.UserRegisteredEvent
import com.tutomato.commerce.domain.cart.CartCommand
import com.tutomato.commerce.domain.cart.CartService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class CartEventListener(
    private val cartService: CartService
) {

    @Async
    @EventListener
    fun onUserRegistered(event: UserRegisteredEvent) {
        cartService.save(CartCommand.Save(event.userId))
    }
}