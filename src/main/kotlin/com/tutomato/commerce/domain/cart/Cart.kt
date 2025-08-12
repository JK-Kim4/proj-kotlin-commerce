package com.tutomato.commerce.domain.cart

import jakarta.persistence.Entity
import jakarta.persistence.Id
import javax.management.InstanceAlreadyExistsException

@Entity
class Cart(
    @Id var id: Long = 0,
    val userId: Long,
    @Transient var cartItems: CartItems = CartItems(),
) {
    fun getItemByIds(productId : Long, optionId : Long) : CartItem? {
        return cartItems.getItemByIds(productId, optionId)
    }

    fun addItem(cartItem: CartItem) {
        if(cartItems.exist(cartItem)) {
            throw InstanceAlreadyExistsException("동일한 옵션의 상품이 이미 등록되어있습니다.")
        }

        this.cartItems = cartItems.addItem(cartItem)
    }

    fun removeItem(cartItem: CartItem) {
        if(!cartItems.exist(cartItem.id)) {
            throw IllegalArgumentException("고유번호에 해당하는 상품이 존재하지않습니다.")
        }

        this.cartItems = cartItems.remove(cartItem)
    }
}