package com.tutomato.commerce.infrastructure.cart

import com.tutomato.commerce.domain.cart.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartJpaRepository : JpaRepository<Cart, Long> {

    fun findByUserId(userId: Long): Cart?

}